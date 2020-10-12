$(document).ready(function () {
    $('#subscribersTable').DataTable();
});

function rowClicked(value) {
    location.href = "/subreddit/" + value;
}
