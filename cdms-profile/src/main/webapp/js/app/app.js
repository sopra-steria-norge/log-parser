var app = app || {};

$(function(){
	new app.GraphView();
});

$('.dropdown-toggle').dropdown();
$('.nav-tabs').button();

$(function() {
	var date = new Date();
    $('#fromDateTimePicker')
    .datetimepicker({
        	language: 'en',
        	endDate: date,
        	todayBtn: true,
        	maskInput: true,
        	pickerPosition: "bottom-left",
        	minuteStep: 3
    })
    .on('changeDate', function(e){
    	console.log(e.date);
    })
});

$(function() {
	var date = new Date();
    $('#toDateTimePicker')
    .datetimepicker({
    	language: 'en',
    	endDate: date,
    	todayBtn: true,
    	maskInput: true,
    	pickerPosition: "bottom-left",
    	minuteStep: 3
	})
	.on('changeDate', function(e){
		console.log(e.date);
	})
});
