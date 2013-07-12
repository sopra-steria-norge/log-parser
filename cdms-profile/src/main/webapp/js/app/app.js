var app = app || {};

$(document).ready(function() {
    var pb = new PageBuilder({
        url: 'last24h',
        container: $('.applicationContainer')
    })
});


$(function(){

	var multiContexts = new app.MultiContexts();
	var procedures = new app.Procedures();
	var timeMeasurements = new app.TimeMeasurements();
	var palette = new Rickshaw.Color.Palette()

	var series = [];
	$.getJSON("/rest/" ,function(result){

		timeMeasurements.set(result)
		timeMeasurements.each(function(tm){
			series.push({name: tm.attributes.procedure.className,
				 		 data:[{x:tm.attributes.timestamp/1000 , y: tm.attributes.duration}],
						 color: palette.color()});

		})
		console.log(series);
		var view = new app.GraphView($('#graph1'), $('#legend1'), 500, 800, series);
		view.render();
		setInterval(function(){
			$.getJSON("/rest/", function(result){
				timeMeasurements.set(result);
				timeMeasurements.each(function(tm){
					for (var i=0; i< view.series.length; i++){
						if(tm.attributes.procedure.className === view.series[i].name){
							view.series[i].data.push({x:tm.attributes.timestamp/1000 , y: tm.attributes.duration});
						}
						if(view.series[i].data.length > 50){
							view.series[i].data.shift();
						}
					}
				})
				view.graph.update();
			})
		}, 1000)
	});


	timeMeasurements.each(function(tm){
		console.log(tm);
	})

//	setInterval(function(){getData()}, 1000)

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
