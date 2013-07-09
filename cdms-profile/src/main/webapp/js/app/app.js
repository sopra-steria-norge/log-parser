var app = app || {};

$(function(){
	new app.GraphView();
});

$('.dropdown-toggle').dropdown();
$('.nav-tabs').button();

$(function() {
    $('#fromDateTimePicker').datetimepicker({
    	language: 'en'
	});
});

$(function() {
    $('#toDateTimePicker').datetimepicker({
    	language: 'en'
	});
});