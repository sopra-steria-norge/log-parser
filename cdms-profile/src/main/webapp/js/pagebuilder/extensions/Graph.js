PageBuilder.extensions.graph = function(container, json) {
    this.g = undefined;
    this.container = container;
    this.svgcontainer;
    this.json = json;
    this.type = Math.random();
    this.prev = 0;
    this.series;
    this.graphOptions = {
    	series: {
    		lines: {
    			show: true,
    			fill: true
    		},
    		points: {
    			show: true
    		}
    	},
    	legend: {
    		show: true,
    	},
    	xaxis: {
    		mode: 'time'
    	}
    };
};
PageBuilder.extensions.graph.prototype.render = function() {
    this.svgcontainer = this.createDOMStructure(this.container, this.json);
    this.draw = this.drawGraph(this.svgcontainer);
    this.startUpdate();
    this.createResizeHandler();
};

PageBuilder.extensions.graph.prototype.createDOMStructure = function(container, json) {
    if (typeof this.svgcontainer !== 'undefined'){
        return this.svgcontainer;
    }
    var newcontainer = document.createElement('div');
    PageBuilder.setAttribute(newcontainer, 'class', json.classes);
    PageBuilder.setAttribute(newcontainer, 'id', json.id);
    container.append(newcontainer);
    return $(newcontainer);
};
PageBuilder.extensions.graph.prototype.drawGraph = function(svgcontainer) {
    if (svgcontainer.length === 0) {
        throw "No svg container found";
    }
    svgcontainer.html('');
    var height = width = $(svgcontainer).width();

    this.series = [{
    	data: [],
    	label: this.json.data.graphOf[0]
    }];

    $.extend(this.graphOptions, this.json.data.graphOptions);

    if (typeof this.draw !== 'undefined') {
        series = this.draw.series;
    }

	svgcontainer.height(svgcontainer.width());
	var graph = $.plot(svgcontainer[0], this.series, this.graphOptions);
    return graph;
};
PageBuilder.extensions.graph.prototype.startUpdate = function() {
    setInterval(function() {
        this.prev += 1;
        if (this.type < 0.33) {
            var data = [this.prev, Math.floor(Math.random() * 40) + 120];
        } else if (this.type < 0.66) {
            var data = [this.prev, this.prev];
        } else {
            var data = [this.prev, Math.sin(this.prev / Math.PI)];
        }

		this.series[0].data.push(data);
		if(this.series[0].data.length > 50)
		{
			this.series[0].data.shift();
		}
		this.draw = $.plot(this.draw.getPlaceholder(), this.series, this.graphOptions )

    }.bind(this), 100);
};
PageBuilder.extensions.graph.prototype.createResizeHandler = function() {
    $(window).on('resize', function() {
        if (typeof this.svgcontainer === 'undefined') {
            return;
        } else {
        	this.svgcontainer.height(this.svgcontainer.width());
        }
    }.bind(this));
};
