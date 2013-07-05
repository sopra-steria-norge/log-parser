/*
	1.

*/

var recSeries = ["Receive", [{x:0.5, y:0.008}, {x:0.8, y:0.012}, {x:0.9, y:0.014}, {x:0.95, y:0.017}, {x:1, y:0.05}]];
var stoSeries = ["Store", [{x:0.5, y:0.008}, {x:0.8, y:0.011}, {x:0.9, y:0.014}, {x:0.95, y:0.017}, {x:1, y:0.07}]];
var waitSeries = ["Wait", [{x:0.5, y:0.658}, {x:0.8, y:4.981}, {x:0.9, y:18.4668}, {x:0.95, y:50.22915}, {x:1, y:60}]];
var tullSeries = ["Pro", [{x:0.5, y:4.658}, {x:0.8, y:4.981}, {x:0.9, y:8.4668}, {x:0.95, y:25.22915}, {x:1, y:30}]]
var tull2Series = ["Pro2", [{x:0.5, y:10}, {x:0.8, y:20}, {x:0.9, y:30}, {x:0.95, y:40}, {x:1, y:5}]]

var palette = new Rickshaw.Color.Palette()

var graph = new Rickshaw.Graph(
{
        element: document.querySelector("#graph"),
        renderer: 'area',
        offset: 'stack',
        width: 900,
        height: 600,
        stroke: true,
        preserve: true,
        padding: { top: 0.06, right: 0.0, bottom: 0.08, left: 0.00 },
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
            },
            {
            	name: tullSeries[0],
            	color: palette.color(),
            	data: tullSeries[1]
            },
            {
            	name: tull2Series[0],
                color: palette.color(),
                data: tull2Series[1]
            }
        ]
} );

var x_axis = new Rickshaw.Graph.Axis.X(
{
	graph: graph,
	ticksTreatment: 'glow'
});

var y_axis = new Rickshaw.Graph.Axis.Y(
{
	graph: graph,
	pixelsPerTick: 30,
	ticksTreatment: 'glow'
});


var hover_detail = new Rickshaw.Graph.HoverDetail(
{
	graph: graph,
	formatter: function(series,x,y) {
		var swatch = '<span class="detail_swatch" style="background-color: ' + series.color + '"></span>';
		return swatch + series.name + "<br>x:" + parseFloat(x) + "<br>y:" + parseFloat(y);
	}
});

var legend = new Rickshaw.Graph.Legend(
{
	element: document.querySelector('#legend'),
	graph: graph
});

var toggler = new Rickshaw.Graph.Behavior.Series.Toggle(
{
	graph: graph,
	legend: legend
});


var order = new Rickshaw.Graph.Behavior.Series.Order(
{
	graph: graph,
	legend: legend
});


var highlighter = new Rickshaw.Graph.Behavior.Series.Highlight(
{
	graph: graph,
	legend: legend
});

var renderController = new RenderController(
{
	graph: graph,
	element: $('.options')
});


graph.render();