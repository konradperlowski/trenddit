let analysisChart = null

async function drawAnalysisChart() {

    let datasets = []
    const limit = parseInt($("#from-days-select").val())
    const dates = [...Array(limit).keys()].map(i => daysAgo(i))

    await $.ajax({
        url: '/api/best/analysis?from=' + limit + '&limit=' + $("#max-rank-select").val(),
        method: 'get',
        dataType: 'json'
    }).done(response => {
        $.each(response, (subreddit, values) => {
            datasets.push({
                label: subreddit,
                data: values,
                backgroundColor: 'rgba(0, 0, 0, 0)',
                borderColor: random_rgb(),
                borderWidth: 3
            })
        })
    })

    if (analysisChart !== null)
        analysisChart.destroy()

    analysisChart = new Chart(document.getElementById('top-analysis').getContext("2d"), {
        type: 'line',
        data: {
            labels: dates.reverse(),
            datasets: datasets
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        reverse: true,
                        callback: value => {
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

function random_rgb() {
    return "rgb(" + Math.floor(Math.random() * 255) + "," + Math.floor(Math.random() * 255) + "," +
        Math.floor(Math.random() * 255) + ")";
}

function daysAgo(days) {
    let date = new Date()
    date.setDate(date.getDate() - days)

    return date.getFullYear() + '-' + String(date.getMonth() + 1).padStart(2, '0') + '-' +
        String(date.getDate()).padStart(2, '0');
}
