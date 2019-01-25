jQuery(function($) {'use strict';

	// Navigation Scroll
	$(window).scroll(function(event) {
		Scroll();
	});

	$('.scrollTo').on('click', function() {
		$('html, body').animate({scrollTop: $(this.hash).offset().top - 35}, 1000);
		return false;
	});

    // $('.navbar-nav>li>a').on('click', function(){
    //     $('.navbar-collapse').collapse('hide');
    // });

	// User define function
	function Scroll() {
		var contentTop      =   [];
		var contentBottom   =   [];
		var winTop      =   $(window).scrollTop();
		var rangeTop    =   200;
		var rangeBottom =   500;
		$('.navbar-collapse').find('.scroll a').each(function(){
			contentTop.push( $( $(this).attr('href') ).offset().top);
			contentBottom.push( $( $(this).attr('href') ).offset().top + $( $(this).attr('href') ).height() );
		})
		$.each( contentTop, function(i){
			if ( winTop > contentTop[i] - rangeTop ){
				$('li.scroll')
				.removeClass('active')
				.eq(i).addClass('active');
			}
		})
	};

	$('#tohash').on('click', function(){
		$('html, body').animate({scrollTop: $(this.hash).offset().top - 5}, 1000);
		return false;
	});

	// accordian
	$('.accordion-toggle').on('click', function(){
		$(this).closest('.panel-group').children().each(function(){
		$(this).find('>.panel-heading').removeClass('active');
		 });

	 	$(this).closest('.panel-heading').toggleClass('active');
	});

	//Slider
	$(document).ready(function() {
		var time = 8; // time in seconds

	 	var $progressBar,
	      $bar,
	      $elem,
	      isPause,
	      tick,
	      percentTime;

	    //Init the carousel
	    // $("#main-slider").find('.owl-carousel').owlCarousel({
	    //   slideSpeed : 500,
	    //   paginationSpeed : 500,
	    //   singleItem : true,
	    //   navigation : true,
			// navigationText: [
			// "<i class='fa fa-angle-left'></i>",
			// "<i class='fa fa-angle-right'></i>"
			// ],
	    //   afterInit : progressBar,
	    //   afterMove : moved,
	    //   startDragging : pauseOnDragging,
	    //   //autoHeight : true,
	    //   transitionStyle : "fadeUp"
	    // });
        //
	    // //Init progressBar where elem is $("#owl-demo")
	    // function progressBar(elem){
	    //   $elem = elem;
	    //   //build progress bar elements
	    //   buildProgressBar();
	    //   //start counting
	    //   start();
	    // }
        //
	    // //create div#progressBar and div#bar then append to $(".owl-carousel")
	    // function buildProgressBar(){
	    //   $progressBar = $("<div>",{
	    //     id:"progressBar"
	    //   });
	    //   $bar = $("<div>",{
	    //     id:"bar"
	    //   });
	    //   $progressBar.append($bar).appendTo($elem);
	    // }
        //
	    // function start() {
	    //   //reset timer
	    //   percentTime = 0;
	    //   isPause = false;
	    //   //run interval every 0.01 second
	    //   tick = setInterval(interval, 10);
	    // };
        //
	    // function interval() {
	    //   if(isPause === false){
	    //     percentTime += 1 / time;
	    //     $bar.css({
	    //        width: percentTime+"%"
	    //      });
	    //     //if percentTime is equal or greater than 100
	    //     if(percentTime >= 100){
	    //       //slide to next item
	    //       $elem.trigger('owl.next')
	    //     }
	    //   }
	    // }
        //
	    // //pause while dragging
	    // function pauseOnDragging(){
	    //   isPause = true;
	    // }
        //
	    // //moved callback
	    // function moved(){
	    //   //clear interval
	    //   clearTimeout(tick);
	    //   //start again
	    //   start();
	    // }
	});

	//Initiat WOW JS
	new WOW().init();
	//smoothScroll
	smoothScroll.init();

	// portfolio filter
	$(window).load(function(){'use strict';
		var $portfolio_selectors = $('.portfolio-filter >li>a');
		var $portfolio = $('.portfolio-items');
		$portfolio.isotope({
			itemSelector : '.portfolio-item',
			layoutMode : 'fitRows'
		});

		$portfolio_selectors.on('click', function(){
			$portfolio_selectors.removeClass('active');
			$(this).addClass('active');
			var selector = $(this).attr('data-filter');
			$portfolio.isotope({ filter: selector });
			return false;
		});
	});

	$(document).ready(function() {
        // $(document).click(function (event) {
        //     var clickover = $(event.target);
        //     var _opened = $(".navbar-collapse").hasClass("navbar-collapse in");
        //     if (_opened === true && !clickover.hasClass("navbar-toggle")) {
        //         $("#xx").click();
        //     }
        // });

        $(document).click(function(e) {
            if (!$(e.target).is('.panel-body')) {
                $('.collapse').collapse('hide');
            }
        });

		//Animated Progress
		$('.progress-bar').bind('inview', function(event, visible, visiblePartX, visiblePartY) {
			if (visible) {
				$(this).css('width', $(this).data('width') + '%');
				$(this).unbind('inview');
			}
		});

		//Animated Number
		$.fn.animateNumbers = function(stop, commas, duration, ease) {
			return this.each(function() {
				var $this = $(this);
				var start = parseInt($this.text().replace(/,/g, ""));
				commas = (commas === undefined) ? true : commas;
				$({value: start}).animate({value: stop}, {
					duration: duration == undefined ? 1000 : duration,
					easing: ease == undefined ? "swing" : ease,
					step: function() {
						$this.text(Math.floor(this.value));
						if (commas) { $this.text($this.text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")); }
					},
					complete: function() {
						if (parseInt($this.text()) !== stop) {
							$this.text(stop);
							if (commas) { $this.text($this.text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")); }
						}
					}
				});
			});
		};

		$('.animated-number').bind('inview', function(event, visible, visiblePartX, visiblePartY) {
			var $this = $(this);
			if (visible) {
				$this.animateNumbers($this.data('digit'), false, $this.data('duration'));
				$this.unbind('inview');
			}
		});
	});

	// Contact form
	var form = $('#contact-form');
	form.submit(function(event){
		event.preventDefault();
		var form_status = $('<div class="form_status"></div>');
        var inputs = $('#contact-form :input');
        var requestBody = {};
        inputs.each(function() {
            requestBody[this.name] = $(this).val();
        });
        requestBody[''] = null;
        console.log(requestBody);
		$.ajax({
			url: '/api/contact/message/send',
			method: 'POST',
            contentType: "application/json; charset=utf-8",
			data: JSON.stringify(requestBody),
			beforeSend: function(){
				form.prepend( form_status.html('<p><i class="fa fa-spinner fa-spin"></i> Email is sending...</p>').fadeIn() );
			}
		}).done(function(data){
			form_status.html('<p class="text-success" style="position:absolute; top: -34px">Thank you for contacting us. We will reply to you as soon as possible.</p>').delay(5000).fadeOut();
		}).fail(function(fail){
            form_status.html('<p class="text-danger" style="position:absolute; top: -34px">Something did not work. Please contact us directly via email or phone.</p>').delay(5000).fadeOut();
		}).always(function() {
            $(':input','#contact-form')
                .not(':button, :submit, :reset, :hidden')
                .val('');
		})
	});

	//Pretty Photo
	$("a[rel^='prettyPhoto']").prettyPhoto({
		social_tools: false
	});

	//Google Map

});