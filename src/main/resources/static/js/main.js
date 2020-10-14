$(document).ready(function () {
    let loading = $('#loadingDiv');
    let content = $('#pageContent');
    loading.css('display', 'block');
    content.css('display', 'none')

    $('#subscribersTable').DataTable();
    $('#growthTableDay').DataTable();
    $('#growthTableWeek').DataTable();
    $('#growthTableMonth').DataTable();

    loading.css('display', 'none');
    content.css('display', 'block')
});
