var app = app || {};

app.GraphView = Backbone.View.extend({
	el: '#graph',

	initialize: function(){
		this.render();
	},

	render: function(){

        var recSeries = ["Receive", [{x:0.5, y:0.008}, {x:0.8, y:0.012}, {x:0.9, y:0.014}, {x:0.95, y:0.017}, {x:1, y:0.05}]];
        var stoSeries = ["Store", [{x:0.5, y:0.008}, {x:0.8, y:0.011}, {x:0.9, y:0.014}, {x:0.95, y:0.017}, {x:1, y:0.07}]];
        var waitSeries = ["Wait", [{x:0.5, y:0.658}, {x:0.8, y:4.981}, {x:0.9, y:18.4668}, {x:0.95, y:50.22915}, {x:1, y:60}]];

        var palette = new Rickshaw.Color.Palette({scheme: 'spectrum2000'})

        var graph = new Rickshaw.Graph(
        {
                element: this.el,
                renderer: 'area',
                offset: 'stack',
                width: 1000,
                height: 800,
                stroke: true,
                preserve: true,
                padding: { top: 0.06, right: 0.0, bottom: 0.08, left: 0.00 },
                series:
                [
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

        var toggle = new Rickshaw.Graph.Behavior.Series.Toggle(
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
	}
});