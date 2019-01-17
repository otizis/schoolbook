$app.rotateDisabled = true;
var lanmuId = 347923083;// 一发儿
var notStr = new Date().getTime();
$ui.render({
    props: {
        title: "网易云电台"
    },
    views: [
        {
            type: "list",
            props: {
                rowHeight: 64.0,
                separatorInset: $insets(0, 5, 0, 0),
                template: [{
                    type: "image",
                    props: {
                        id: "image"
                    },
                    layout: function (make, view) {
                        make.left.top.bottom.inset(5);
                        make.width.equalTo(view.height);
                    }
                },
                {
                    type: "label",
                    props: {
                        id: "label",
                        font: $font("bold", 17),
                        lines: 0
                    },
                    layout: function (make) {
                        make.left.equalTo($("image").right).offset(10)
                        make.top.bottom.equalTo(0)
                        make.right.inset(10)
                    }
                }
                ]
            },
            layout: $layout.fill,
            events: {
                didSelect: function (tableView, indexPath) {
                    openURL(tableView.object(indexPath))
                },
                pulled: function (sender) {
                    refetch()
                }
            }
        }]
})
function refetch() {
    $http.get({
        url: "https://api.imjad.cn/cloudmusic/?type=djradio&id=" + lanmuId + "&_t=" + notStr,
        handler: function (resp) {
            render(resp.data.programs)
        }
    })
}

function render(programs) {
    var data = []
    for (var idx in programs) {
        var program = programs[idx]
        data.push({
            url: "https://api.imjad.cn/cloudmusic/?type=dj&id=" + program.id + "&_t=" + notStr,
            image: {
                src: program.coverUrl
            },
            label: {
                text: program.name
            },
            id: program.id
        })
    }
    $("list").data = data
    $("list").endRefreshing()
}

function openURL(program) {
    $console.info(JSON.stringify($file.list("")))
    $console.info(JSON.stringify($file.list("download")))
    $console.info(JSON.stringify($file.list("download/"+program.id)))
    if($file.exists("download/"+program.id)){
        
        $ui.push({
            props: {
                title: program.name
            },
            views: [
                {
                    type: "web",
                    props: {
                        html: "<html><head><meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=device-dpi' />"
                            + "</head><body><audio id='audio' autoplay loop controls='controls' src='" + data.data[0].url + "'></audio>"
                            + "<br><div class='c'><button id='play'>播放</button><button onclick='add(0.1)'>加速</button><button onclick='add(-0.1)'>降速</button></div>"
                            + "播放速率：<span id='rate'>1</span></body></html>",
                        script: function () {
                            var rateDom = document.getElementById("rate");
                            function setPlayRate(rate) {
                                document.getElementById("audio").playbackRate = rate;
                                rateDom.innerText = rate;
                            }
                            document.getElementById("play").onclick = function () {
                                document.getElementById("audio").play();
                                setPlayRate(1.2)
                            }
                            function add(t) {
                                var r = new Number(rateDom.innerText) + t;
                                r = r.toFixed(2);
                                setPlayRate(new Number(r));
                            }
                        },
                        style: ".c{padding-top:20%}button{width:110px;height:70px;font-size:20px}audio{width:100%;height:100px}span{font-size:40px}"
                    },
                    layout: $layout.fill
                }
            ]
        })
    }else{
        $http.get({
            url: program.url,
            handler: function (resp) {
                var data = resp.data;
                $console.info(data.data[0].url);
                var mp3Url = data.data[0].url;
                $http.download({
                    url: mp3Url,
                    progress: function (bytesWritten, totalBytes) {
                        var percentage = bytesWritten * 1.0 / totalBytes
                        $ui.progress(percentage)
                    },
                    handler: function (resp) {
                        $ui.progress(2);
                        var paths = mp3Url.split("/");
                        var filename = paths[paths.length - 1];
                        if($file.exists("download")){
                            $file.mkdir("download")
                        }
                        if($file.exists("download/"+program.id)){
                            $file.mkdir("download/"+program.id)
                        }
                        $file.write({
                            data: resp.data,
                            path: "download/"+program.id+"/" + filename
                        })
                        $ui.alert("保存成功")
                    }
                })
            }})
    }


}
refetch()