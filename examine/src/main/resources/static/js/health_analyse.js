$("#todayInfo").hide();
$("#exceptionTrace").hide();
$("#importStudent").hide();
$("#healthInfo").hide();
function analyse(element) {
    if ($(element).val() == "今日异常") {
        $("#exceptiontable").show();
        $("#todayInfo").hide();
        $("#healthInfo").hide();
        $("#exceptionTrace").hide();
        $("#importStudent").hide();
    } else if ($(element).val() == "今日上报") {
        $("#exceptiontable").hide();
        $("#todayInfo").show();
        $("#healthInfo").hide();
        $("#exceptionTrace").hide();
        $("#importStudent").hide();
        todayInfo();
    } else if ($(element).val() == "异常追踪") {
        $("#exceptiontable").hide();
        $("#todayInfo").hide();
        $("#healthInfo").hide();
        $("#exceptionTrace").show();
        $("#importStudent").hide();
    }else if($(element).val()=="上报详情"){
        $("#exceptiontable").hide();
        $("#todayInfo").hide();
        $("#healthInfo").show();
        $("#exceptionTrace").hide();
        $("#importStudent").hide();
    }
    else {
        $("#exceptiontable").hide();
        $("#todayInfo").hide();
        $("#exceptionTrace").hide();
        $("#healthInfo").hide();
        $("#importStudent").show();
        marked();
    }
}

function commit(element) {
    var td = ($(element).parent().siblings());
    var id = ($(td[0]).text());
    var flag = 0;
    if ($(element).text() == "标注") {
        flag = 1;
    }
    var data = {id: id, flag: flag};
    $.ajax({
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/healthAnalyse/mark",
        data: JSON.stringify(data),
        success: function (result) {
            console.log("success");
        },
        error: function (result) {
            var url = (result.responseText);
        }
    });
    $(element).parent().parent().remove();
}

function todayInfo() {
    var map01 = echarts.init(document.getElementById('map01'));
    var map02 = echarts.init(document.getElementById('map02'));
    var option01 = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            left: 10,
            data: ['健康', '异常', '未上报']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series: [
            {
                name: '上报情况',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: [
                    {value: health[health.length - 1], name: '健康'},
                    {value: exception[exception.length - 1], name: '异常'},
                    {value: offline[offline.length - 1], name: '未上报'}
                ]
            }
        ],
        color:['#1abc9c','#c0392b','#34495e']
    };
    map01.setOption(option01);
    window.addEventListener("resize", function () {
        map01.resize();
    });

    var option02 = {
        title: {
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series: [
            {
                name: '今日情况',
                type: 'pie',
                radius: [30, 170],
                center: ['55%', '50%'],
                roseType: 'area',
                data: [
                    {value: health[health.length - 1], name: '健康'},
                    {value: exception[exception.length - 1], name: '异常'},
                    {value: offline[offline.length - 1], name: '未上报'}
                ]
            }
        ],
        color:['#1abc9c','#c0392b','#34495e']
    };
    map02.setOption(option02);
    window.addEventListener("resize", function () {
        map02.resize();
    });
}

function marked() {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/healthAnalyse/marked",
        success: function (result) {

            $("#content").empty();

            for (var i = 0; i < result.length; i++) {
                var id = $("<td></td>").text(result[i].id);
                var name = $("<td></td>").text(result[i].name);
                var email = $("<td></td>").text(result[i].email);
                var dormitory = $("<td></td>").text(result[i].dormitory);
                var labid = $("<td></td>").text(result[i].labid);
                var detail = $("<td><button onclick='markdetail(this)' class='btn bg-primary'>详细情况</button></td>");
                var unmark = $("<td><button onclick='commit(this)' class='btn bg-success'>取消标注</button></td>");
                var tr = $("<tr></tr>");
                var phone = $("<td></td>").text(result[i].phone);
                tr.append(id, name,phone, email, dormitory, labid, detail, unmark);
                $("#content").append(tr);
            }


        },
        error: function (result) {
            console.log(result);
        }
    });
}

function markdetail(element) {
    var td = $(element).parent().siblings();
    window.location.href = '/healthAnalyse/markDetail?id=' + $(td[0]).text();
}


