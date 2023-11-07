let chartList = []

async function createChart() {
    var ctx = $("#temp").get(0).getContext("2d");
    var ctx2 = $("#humid").get(0).getContext("2d");
    if (chartList[0]) chartList[0].destroy();
    if (chartList[1]) chartList[1].destroy();
    const tempConfig = await getTempConfig()
    const humidConfig = await getHumidConfig()
    console.log(tempConfig)
    chartList[0] = new Chart(ctx, tempConfig)
    chartList[1] = new Chart(ctx2, humidConfig)
}

async function getTempConfig() {
    const [labelSet, dataSet] = await getTemperature();
    const data = {
        datasets: [{
            backgroundColor: 'black',
            borderColor: '#2d84b4',
            borderWidth: 2,
            fill: false,
            pointBorderColor: 'rgba(0, 0, 0, 0)',
            pointBackgroundColor: 'rgba(0, 0, 0, 0)',
            pointHoverBackgroundColor: 'rgba(0,0,0,0)',
            pointHoverBackgroundWidth: 5,
            pointHoverBorderColor: 'rgba(45, 132, 180, 0.5)',
            pointHoverBorderWidth: 4,
            pointStyle: 'circle',
            pointHoverRadius: 5.5,
            data: dataSet,
        }],
            labels: labelSet,
    };

    const config = {
        type: 'line',
        data: data,
        options: {
            scales: {
                x: {
                    stacked : true,
                    ticks: {
                        autoSkip: true,
                        maxTicksLimit: 6,
                        maxRotation: 0,
                        callback: function(value) {
                            let labelValue = this.getLabelForValue(value);
                            if(selectedZeitspanne == 60) {
                                return labelValue.slice(-8);
                            } else if (selectedZeitspanne < 10080){
                                return labelValue.slice(-8,-3);
                            } else {
                                return labelValue.slice(0,7)
                            }
                        }
                    },
                    grid: {
                      display: true,
                      drawBorder: false,
                      drawOnChartArea: false,
                      drawTicks: true,
                      tickLength: 3,
                    },
                },
                yAxes: {
                        ticks: {
                            autoSkip: true,
                            maxTicksLimit: 6,
                        },
                        grid: {
                            drawBorder: false,
                            drawTicks: false
                        }
                    },
                },
                elements: {
                    point: {
                        radius: 5
                    }
                },
                plugins: {
                  tooltip: {
                    displayColors: false,
                    backgroundColor: 'rgba(45,132,180,0.8)',
                    bodyFontColor: 'rgb(255,255,255)',
                    callbacks: {
                      title: () => {
                        return
                      },
                      label: (ttItem) => ( `${ttItem.parsed.y} °C` ),
                      afterBody: (ttItems) => (ttItems[0].label)
                    }
                  },
                  legend: {
                    display: false
                  }
                }
              }
            };
    return config;
}

async function getHumidConfig() {
    const [labelSet, dataSet] = await getHumidity();
    const data = {
        datasets: [{
            backgroundColor: 'black',
            borderColor: '#2d84b4',
            borderWidth: 2,
            fill: false,
            pointBorderColor: 'rgba(0, 0, 0, 0)',
            pointBackgroundColor: 'rgba(0, 0, 0, 0)',
            pointHoverBackgroundColor: 'rgba(0,0,0,0)',
            pointHoverBackgroundWidth: 5,
            pointHoverBorderColor: 'rgba(45, 132, 180, 0.5)',
            pointHoverBorderWidth: 4,
            pointStyle: 'circle',
            pointHoverRadius: 5.5,
            data: dataSet,
        }],
            labels: labelSet,
    };

    const config = {
        type: 'line',
        data: data,
        options: {
            scales: {
                x: {
                    stacked : true,
                    ticks: {
                        autoSkip: true,
                        maxTicksLimit: 6,
                        maxRotation: 0,
                        callback: function(value) {
                            let labelValue = this.getLabelForValue(value);
                            if(selectedZeitspanne == 60) {
                                return labelValue.slice(-8);
                            } else if (selectedZeitspanne < 10080){
                                return labelValue.slice(-8,-3);
                            } else {
                                return labelValue.slice(0,7)
                            }
                        }
                    },
                    grid: {
                      display: true,
                      drawBorder: false,
                      drawOnChartArea: false,
                      drawTicks: true,
                      tickLength: 3,
                    },
                },
                yAxes: {
                        ticks: {
                            autoSkip: true,
                            maxTicksLimit: 6,
                        },
                        grid: {
                            drawBorder: false,
                            drawTicks: false
                        }
                    },
                },
                elements: {
                    point: {
                        radius: 5
                    }
                },
                plugins: {
                  tooltip: {
                    displayColors: false,
                    backgroundColor: 'rgba(45,132,180,0.8)',
                    bodyFontColor: 'rgb(255,255,255)',
                    callbacks: {
                      title: () => {
                        return
                      },
                      label: (ttItem) => ( `${ttItem.parsed.y} %` ),
                      afterBody: (ttItems) => (ttItems[0].label)
                    }
                  },
                  legend: {
                    display: false
                  }
                }
              }
            };
    return config;
}

function tempChart(labels, data, tooltip) {
    var ctx2 = $("#temp").get(0).getContext("2d");
    if(chartList[0]){
        chartList[0].destroy();
    }
    chartList[0] = new Chart(ctx2, {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                    label: "Temperatur",
                    data: data,
                    backgroundColor: "rgba(0, 156, 255, .5)",
                    fill: true
                }
            ]
            },
        options: {
            plugins: {
                tooltip: {
                    callbacks: {
                        title: function(tooltipItem) {
                            return tooltip;
                        },
                    }
                }
            },
            responsive: true
        }
    });
}

function humidChart(labels, data, tooltip) {
    var ctx2 = $("#humid").get(0).getContext("2d");
    if(chartList[1]){
        chartList[1].destroy();
    }
    chartList[1] = new Chart(ctx2, {
        type: "line",
        data: {
            labels: labels, //links = frühester Wert / rechts = aktuellster Wert
            datasets: [{
                    label: "Luftfeuchtigkeit",
                    data: data,
                    backgroundColor: "rgba(0, 156, 255, .5)",
                    fill: true
                }
            ]
            },
        options: {
            plugins: {
                tooltip: {
                    callbacks: {
                        title: function(tooltipItem) {
                            return tooltip[tooltipItem];
                        },
                    }
                }
            },
            responsive: true
        }
    });
}

async function refresher() {
    let timespan = selectedZeitspanne;
    /*const [labelsTemp, dataTemp, tooltipTemp] = await getTemperature();
    const [labelsHumid, dataHumid, tooltipHumid] = await getHumidity();
    tempChart(labelsTemp, dataTemp, tooltipTemp);
    humidChart(labelsHumid, dataHumid, tooltipHumid);*/
    createChart();

    const latestTemp = await latestTemperature();
    const latestHumid = await latestHumidity();
    document.getElementById("latest_humidity").innerHTML = latestHumid+" %";
    document.getElementById("latest_temperature").innerHTML = latestTemp+" °C";
    await Sleep();
    if (timespan == selectedZeitspanne) refresher();
}

function Sleep() {
    return new Promise(resolve => setTimeout(resolve, 10000))
}