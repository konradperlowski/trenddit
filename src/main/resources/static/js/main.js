$(document).ready(function () {
    let loading = $('#loadingDiv');
    let content = $('#pageContent');
    content.hide()
    loading.show()

    $('#subscribersTable').DataTable();
    $('#growthTableDay').DataTable();
    $('#growthTableWeek').DataTable();
    $('#growthTableMonth').DataTable();
    $('#commentsTableDay').DataTable();
    $('#commentsTableWeek').DataTable();
    $('#commentsTableMonth').DataTable();
    $('#postsTableDay').DataTable();
    $('#postsTableWeek').DataTable();
    $('#postsTableMonth').DataTable();

    content.show()
    loading.hide()
});
