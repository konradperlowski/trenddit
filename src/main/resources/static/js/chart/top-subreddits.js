let chart = null

async function drawTopSubredditChart() {
    let names = []
    let values = []

    await $.ajax({
        url: '/api/best/' + $("#periodOfTimeSelect").val(),
        method: 'get',
        dataType: 'json'
    }).done(response => {
        response.forEach(subreddit => {
            names.push(subreddit.name)
            values.push(subreddit.number)
        })
    })

    if (chart !== null)
        chart.destroy()

    chart = new Chart(document.getElementById("top-subreddits").getContext("2d"), {
        type: 'bar',
        data: {
            labels: names,
            datasets: [{
                label: 'Number of posts',
                data: values,
                backgroundColor: 'rgba(0, 123, 255, 0.6)',
                borderColor: 'rgba(0, 123, 255, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    })
}
