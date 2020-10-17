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
                backgroundColor: 'rgba(0, 123, 255, 0.3)',
                borderColor: 'rgba(0, 123, 255, 1)',
                borderWidth: 2
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: false
                    }
                }]
            }
        }
    })
}