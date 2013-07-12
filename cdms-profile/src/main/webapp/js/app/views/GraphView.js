var app = app || {};

app.GraphView = Backbone.View.extend({

	initialize: function(element, legend, height, width, series){
		this.el = element;
		this.leg = legend;
		this.height = height;
		this.width = width;
		this.series = series;
		this.graph = null;
	},

	render: function(){

        var palette = new Rickshaw.Color.Palette()

        var graph = new Rickshaw.Graph(
        {
                element: this.el[0],
                renderer: 'area',
                offset: 'stack',
                width: this.width,
                height: this.height,
                stroke: true,
                preserve: true,
                padding: { top: 0.06, right: 0.0, bottom: 0.08, left: 0.00 },
                series: this.series
        } );

        this.graph = graph;

        var x_axis = new Rickshaw.Graph.Axis.Time(
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
        		return swatch + series.name + "<br>Time:" + Date(x) + "<br>Duration:" + parseFloat(y);
        	}
        });

        var legend = new Rickshaw.Graph.Legend(
        {
        	element: this.leg[0],
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