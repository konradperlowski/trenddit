function changeGrowthTable() {
    let periodOfTime = $('#growthTableSelect').val();
    $('#growthTableDayDiv').css('display', 'none');
    $('#growthTableWeekDiv').css('display', 'none');
    $('#growthTableMonthDiv').css('display', 'none');
    $('#' + periodOfTime).css('display', 'block');
}
