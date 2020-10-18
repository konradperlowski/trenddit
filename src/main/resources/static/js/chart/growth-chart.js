async function drawGrowthChart(metric, subreddit, canvasId) {
    let dates = []
    let values = []

    await $.ajax({
        url: '/api/growth/' + metric + '/' + subreddit,
        method: 'get',
        dataType: 'json'
    }).done(response => {
        response.forEach(subreddit => {
            dates.push(subreddit.date)
            values.push(subreddit.number)
        })
    })

    new Chart(document.getElementById(canvasId).getContext("2d"), {
        type: 'line',
        data: {
            labels: dates,
            datasets: [{
                label: 'Subscribers',
                data: values,
                backgroundColor: 'rgba(0, 123, 255, 0)',
                borderColor: 'rgba(0, 123, 255, 1)',
                borderWidth: 3
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: false
                    }
                }],
                xAxes: [{
                    afterTickToLabelConversion: data => {
                        const xLabels = data.ticks;
                        xLabels.forEach((labels, i) => {
                            if (i % 2 === 1) {
                                xLabels[i] = '';
                            }
                        });
                    }
                }]
            }
        }
    })
}