let analysisChart = null

async function drawAnalysisChart() {

    let subredditDict = {}
    let names = []
    let namesOccurrences = {}
    let dates = []
    let datasets = []

    await $.ajax({
        url: '/api/best/analysis?from=' + $("#from-days-select").val() + '&limit=' + $("#max-rank-select").val(),
        method: 'get',
        dataType: 'json'
    }).done(response => {
        for (let data in response) {
            dates.push(data)
            response[data].forEach(s => namesOccurrences[s.name] = (namesOccurrences[s.name] || 0) + 1)
        }

        let len = dates.length;

        for (let name in namesOccurrences) {
            if (namesOccurrences[name] === len)
                names.push(name)
        }

        names.forEach(name => {
            subredditDict[name] = []
            for (let data in response) {
                let isPresent = false
                response[data].forEach(s => {
                    if (s.name === name) {
                        isPresent = true
                        subredditDict[name].push(s.number)
                    }
                })
                if (!isPresent)
                    subredditDict[name].push(null)
            }
        })
    })

    for (let subreddit in subredditDict) {
        datasets.push({
            label: subreddit,
            data: subredditDict[subreddit],
            backgroundColor: 'rgba(0, 0, 0, 0)',
            borderColor: random_rgb(),
            borderWidth: 3
        })
    }

    drawChart(dates, datasets)
}

function random_rgb() {
    return "rgb(" + Math.floor(Math.random() * 255) + "," +
        Math.floor(Math.random() * 255) + "," +
        Math.floor(Math.random() * 255) + ")";
}


function drawChart(dates, datasets) {
    if (analysisChart !== null)
        analysisChart.destroy()

    analysisChart = new Chart(document.getElementById('top-analysis').getContext("2d"), {
        type: 'line',
        data: {
            labels: dates,
            datasets: datasets
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        reverse: true,
                        callback: function (value) {
                            if (value % 1 === 0) {
                                return value;
                            }
                        }
                    }
                }],
                xAxes: [{
                    afterTickToLabelConversion: data => {
                        const xLabels = data.ticks;
                        xLabels.forEach((labels, i) => {
                            if (i % 3 !== 0) {
                                xLabels[i] = '';
                            }
                        });
                    }
                }]
            }
        }
    })
}
