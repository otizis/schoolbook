$app.rotateDisabled = true;
var lanmuId = 347923083;// 一发儿
$ui.render({
    props: {
        title: "网易云电台陈一发"
    },
    views: [{
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
                    make.left.top.bottom.inset(5)
                    make.width.equalTo(view.height)
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
                openURL(tableView.object(indexPath).url, tableView.object(indexPath).label.text)
            },
            pulled: function (sender) {
                refetch()
            }
        }
    }]
})
$http.get({
    url: '',
    handler: function (resp) {
        var data = resp.data;
        data.programs
    }
})
function refetch() {
    $http.get({
        url: "https://api.imjad.cn/cloudmusic/?type=djradio&id="+lanmuId,
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
            url: "https://api.imjad.cn/cloudmusic/?type=dj&id=" + program.id,
            image: {
                src: program.coverUrl
            },
            label: {
                text: program.name
            }
        })
    }
    $("list").data = data
    $("list").endRefreshing()
}

function openURL(url, name) {
    $http.get({
        url: url,
        handler: function (resp) {
            var data = resp.data;
            $console.info(data.data[0].url);
            $ui.push({
                props: {
                    title: name
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
                            style: ".c{padding-top:20%}button{width:110px;height:70px;font-size:30px}audio{width:100%;height:100px}span{font-size:40px}"
                        },
                        layout: $layout.fill
                    }
                ]
            })
        }
    })
}
refetch()