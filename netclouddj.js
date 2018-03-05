$app.rotateDisabled = true;
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
        url: "https://api.imjad.cn/cloudmusic/?type=djradio&id=347923083",
        handler: function (resp) {
            render(resp.data.programs)
            $cache.set("programs", resp.data.programs)
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
                                + "<br><div class='c'><button id='play'>播放</button><button id='add'>加速</button><button id='cut'>降速</button></div>"
                                + "<p id='rate'>1</p></body></html>",
                            script: function () {
                                var rateDom = document.getElementById("rate");
                                function setPlayRate(rate) {
                                    document.getElementById("audio").playbackRate = rate;
                                    rateDom.innerText = rate;
                                }
                                document.getElementById("play").onclick = function () {
                                    document.getElementById("audio").play();
                                }
                                document.getElementById("add").onclick = function () {
                                    setPlayRate(new Number(rateDom.innerText) + 0.1);
                                }
                                document.getElementById("cut").onclick = function () {
                                    setPlayRate(new Number(rateDom.innerText) - 0.1);
                                }
                            },
                            style: ".c{padding-top:20%}button{width:300px;height:200px;font-size:30px}audio{width:100%;height:100px}p{font-size:40px}"
                        },
                        layout: $layout.fill
                    }
                ]
            })
        }
    })



}

var cache = $cache.get("programs")

if (cache) {
    render(cache)
}

refetch()

