async function drawTopSubredditChart() {

    let names = []
    let values = []

    await fetch('/api/best/month')
        .then(response => response.json())
        .then(data => {
            data.forEach(function (subreddit) {
                names.push(subreddit.name)
                values.push(subreddit.number)
            });
        });

    new Chart(document.getElementById("top-subreddits").getContext("2d"), {
        type: 'bar',
        data: {
            labels: names,
            datasets: [{
                label: "Number of posts",
                data: values,
                backgroundColor: 'rgba(2, 36, 255, 0.2)',
                borderColor: 'rgba(2, 36, 255, 0.5)',
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
