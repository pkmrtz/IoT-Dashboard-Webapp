function tempChart(labels, data) {
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
            responsive: true
        }
    });
}

function humidChart(labels, data) {
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
            scales: {
                x: {
                    ticks: {
                         callback: function(value, index, values) {
                            var date = String(value)
                            if (values.length > 1) {
                                return date.toLocaleDateString('de-DE', { day: 'numeric', month: 'short' }); // zeige nur den Tag an
                            } else {
                                return date.toLocaleString('de-DE'); // zeige das volle Datum an
                            }
                        } 
                    }
                },
            },
            plugins: {
                tooltip: {
                    callbacks: {
                        title: function(tooltipItem) {
                            return tooltipItem[0].label;
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
    const labelsTemp = await getTemperature(true);
    const dataTemp = await getTemperature(false);
    const labelsHumid = await getHumidity(true);
    const dataHumid = await getHumidity(false);
    tempChart(labelsTemp, dataTemp);
    humidChart(labelsHumid, dataHumid);

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

let chartList = []