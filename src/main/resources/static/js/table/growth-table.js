function changeGrowthTable() {
    $('#growthTableDayDiv').hide()
    $('#growthTableWeekDiv').hide()
    $('#growthTableMonthDiv').hide()
    $('#' + $('#growthTableSelect').val()).show()
}
