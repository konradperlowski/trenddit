let chart = null

async function drawTopSubredditChart() {
    let names = []
    let values = []

    await fetch('/api/best/' + document.getElementById("periodOfTimeSelect").value)
        .then(response => response.json())
        .then(data => {
            data.forEach(function (subreddit) {
                names.push(subreddit.name)
                values.push(subreddit.number)
            });
        });

    if (chart !== null)
        chart.destroy()

    chart = new Chart(document.getElementById("top-subreddits").getContext("2d"), {
        type: 'bar',
        data: {
            labels: names,
            datasets: [{
                label: "Number of posts",
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
