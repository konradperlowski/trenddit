function changePostTable() {
    $('#postsTableDayDiv').hide()
    $('#postsTableWeekDiv').hide()
    $('#postsTableMonthDiv').hide()
    $('#' + $('#postsTableSelect').val()).show()
}
