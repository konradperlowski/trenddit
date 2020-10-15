$(document).ready(function () {
    let loading = $('#loadingDiv');
    let content = $('#pageContent');
    content.hide()
    loading.show()

    $('#subscribersTable').DataTable();
    $('#growthTableDay').DataTable();
    $('#growthTableWeek').DataTable();
    $('#growthTableMonth').DataTable();

    content.show()
    loading.hide()
});
