$ui.render({
    props: {
        title: "网易云电台"
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
                openURL(tableView.object(indexPath).url, tableView.object(indexPath).name)
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
            var data = resp.data
            $ui.push({
                props: {
                    title: name
                },
                views: [
                    {
                        type: "web",
                        props: {
                            html: "<audio id='audio' loop controls='controls' src='"+ data.data[0].url+"'></audio>"
                                +"<br><button id='play'>播放</button>"
                                + "<input type='number' id='rate' value='1'></input>",
                            script: function () {
                                function setPlayRate(params) {
                                    document.getElementById("audio").playbackRate=params.rate;
                                    document.getElementById("rate").value=params.rate;
                                }
                                document.getElementById("play").onclick=function () {
                                    document.getElementById("audio").play();
                                }
                            },
                            style: "button{width:400px;height:400px;font-size:30px}audio{width:100%;height:100px}span{font-size:30px}"
                        },
                        layout: function (make, views) {
                            make.top.equalTo(0);
                            make.height.equalTo(200)
                            make.width.equalTo(views.super)
                        }
                    },
                    {
                        type: "stepper",
                        props: {
                            max: 20,
                            min: 10,
                            value: 12,
                            step:1
                        },
                        layout: function (make, view) {
                            make.centerX.equalTo(view.super);
                            make.top.equalTo(240);
                        },
                        events: {
                            changed: function (sender) {
                                var rate = $("stepper").value / 10;
                                $("web").eval(
                                    {
                                        script: 'document.getElementById("audio").playbackRate=' + rate
                                    }
                                )
                            }
                        }
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

