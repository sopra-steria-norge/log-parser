/*
	1.

*/

var recSeries = ["Receive", [{x:0.5, y:0.008}, {x:0.8, y:0.012}, {x:0.9, y:0.014}, {x:0.95, y:0.017}]];
var stoSeries = ["Store", [{x:0.5, y:0.008}, {x:0.8, y:0.011}, {x:0.9, y:0.014}, {x:0.95, y:0.017}]];
var waitSeries = ["Wait", [{x:0.5, y:0.658}, {x:0.8, y:4.981}, {x:0.9, y:18.4668}, {x:0.95, y:50.22915}]];

var palette = new Rickshaw.Color.Palette()

var graph = new Rickshaw.Graph( {
        element: document.querySelector("#graph"),
        renderer: 'area',
        width: 900,
        height: 600,
        stroke: true,
        interpolation: false,
        padding: { top: 0.06, right: 0.02, bottom: 0.08, left: 0.02 },
        offset:'value',
        series: [
        	{
                data: recSeries[1],
                color: palette.color(),
                name: recSeries[0]
            },
            {
            	data: stoSeries[1],
            	color: palette.color(),
            	name: stoSeries[0]
            },
            {
            	name: waitSeries[0],
            	color: palette.color(),
            	data: waitSeries[1]
            }
        ]
} );

var x_axis = new Rickshaw.Graph.Axis.X({
	graph: graph
});

var y_axis = new Rickshaw.Graph.Axis.Y({
	graph: graph,
	pixelsPerTick: 30
});


var hover_detail = new Rickshaw.Graph.HoverDetail({
	graph: graph,
	formatter: function(series,x,y) {return "x:" + parseFloat(x) + "<br>y:" + parseFloat(y);}
});

var legend = new Rickshaw.Graph.Legend({
	element: document.querySelector('#legend'),
	graph: graph
});

var toggler = new Rickshaw.Graph.Behavior.Series.Toggle({
	graph: graph,
	legend: legend
});


var order = new Rickshaw.Graph.Behavior.Series.Order({
	graph: graph,
	legend: legend
});


var highlighter = new Rickshaw.Graph.Behavior.Series.Highlight({
	graph: graph,
	legend: legend
});


graph.render();