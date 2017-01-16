function loadWeeks() {
    const NUM_WEEKS = 4;

    for (var i = 0; i < NUM_WEEKS; i++) {
        var startDate = moment().subtract(i, 'weeks').startOf('isoweek');
        var endDate = moment().subtract(i, 'weeks').endOf('isoweek');

        var startDisplay = moment(startDate).format('MMM Do, YYYY');
        var endDisplay = moment(endDate).format('MMM Do, YYYY');

        var startValue = moment(startDate).format('YYYY-MM-DD');
        var endValue = moment(endDate).format('YYYY-MM-DD');

        var option = $('<option>');
        $('#week-select').append(option);
        option.addClass('week-option')
            .val(i)
            .attr('data-start-date', startValue)
            .attr('data-end-date', endValue)
            .text(startDisplay + "    to    " + endDisplay);
    }

    var selected = $('#week-select').find(':selected');
    var startDate = selected.data('start-date');
    var endDate = selected.data('end-date');
    displayTrendData(startDate, endDate);

}

function displayTrendData(startDate, endDate) {
    var baseUrl = 'api/trends?';
    var startDateParam = 'startDate=' + startDate;
    var endDateParam = 'endDate=' + endDate;

    var url = baseUrl + startDateParam + '&' + endDateParam;

    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        success: function(data) {

            if (data.length === 0) {
                $('#message').text('There is no data for the selected week.');
            } else {
                $('#message').text('');
            }
            // Display list
            $('#trending-items-list').empty();
            for (var i = 0; i < data.length; i++) {
                var listItem = $('<li>');
                listItem.text(data[i].item.name);
                if (data[i].status == 'NEW') {
                    listItem.append($('<span class="new-item"> - NEW! </span>'));
                }
                $('#trending-items-list').append(listItem.clone());
            }
        },
        error: function(error) {
            console.log(error);
            $('#message').text('An error occurred while processing the request');
        }
    });
}

$(document).ready(function() {
    loadWeeks();

    $('#week-select').change(function() {
        var selected = $(this).find(':selected');
        var startDate = selected.data('start-date');
        var endDate = selected.data('end-date');
        displayTrendData(startDate, endDate);
    });
});

