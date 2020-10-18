function changeCommentTable() {
    $('#commentsTableDayDiv').hide()
    $('#commentsTableWeekDiv').hide()
    $('#commentsTableMonthDiv').hide()
    $('#' + $('#commentsTableSelect').val()).show()
}
