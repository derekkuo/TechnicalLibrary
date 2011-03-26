/*
<script type="text/javascript">
 */
	$(function(){
		var removeAD = false;//是否去除广告
		var topDirectIndex = 3;//<h3>章、节上添加TOP  <h4>章、节、点上添加TOP
		
		//去除广告
		if(removeAD){
			var $ad = $("#ad-topic-tags");
			$ad.remove();
			$ad = $("#ad-topic-author");
			$ad.remove();
		}
		
		//生成文章末尾的NOTE部分BEGIN
		var $notes = $(".techlib-note");
		var $blockNote = $("#techlib-block-note");
		if( $notes.length > 0){
			$blockNote.append("<a name='note'></a>");
			$blockNote.append("<menu><h2>NOTE</h2></menu>");
		}
		$notes.each(function(i){
			j=i+1;
			$blockNote.append("<div style='margin-bottom:10px;'>"+ $(this).html()+"</div>");
		});
		//生成文章末尾的NOTE部分END

		//生成目录
		var $menus = $("menu");
		var $menubarBlock = $("#techlib-block-menubar");
		$menubarBlock.append("<div id='techlib-menubar'><div style='font-size:16px\; font-weight:bold\; margin-top:15px\; margin-bottom:15px\;'>目录</div><UL class='techlib-menubar-ul'></UL></div>");
		var $menubarUl = $(".techlib-menubar-ul");
		$menus.each(function(i){
			i++;
			//添加目录
			$menubarUl.append("<LI><A href='#"+i+"'>"+$(this).html()+"</A></LI>");
			//正文中添加锚点
			if(i!=1 && i!=$menus.length)//开头和最后附录不加TOP跳转
				if( $(this).html().substring(2,3) <= topDirectIndex)
					$(this).prepend("<div style='text-align: right\;'><a href='#top'>TOP</a></div>");
			$(this).prepend("<a name='"+i+"'></a>");
		});
		
		//生成章以及节的二层目录
		/*
		var $chapters = $(".part");
		$chapters.each(function(i){
			$("#menu").empty();
			$("#menu").append( $(this).html() );
			var $chapter = $("#menu > .chapter");
			//alert($chapter.text());
			$("#menu").empty();
			$("#menu").append( $(this).html() );
			var $sections = $("#menu > .section");
			$sections.each(function(j){
				//alert($(this).text());
			});
		});
		$("#menu").remove();
		*/
		
		//生成目录
		/*
		var $h2s = $("h2");
		var $menubarBlock = $("#techlib-block-menubar");
		$menubarBlock.append("<div id='techlib-menubar'><div style='font-size:16px\; font-weight:bold\; margin-top:15px\; margin-bottom:15px\;'>目录</div><UL class='techlib-menubar-ul'></UL></div>");
		var $menubarUl = $(".techlib-menubar-ul");
		$h2s.each(function(i){
			i++;
			//添加目录
			$menubarUl.append("<LI><A href='#"+i+"'>"+$(this).html()+"</A></LI>");
			//正文中添加锚点
			if(i!=1 && i!=$h2s.length)//1第一节不打印 i!=$h2s.length最后一节不打印
				$(this).prepend("<div style='text-align: right\;'><a href='#top'>TOP</a></div>");
			$(this).prepend("<a name='"+i+"'></a>");
		});
		*/
	});
/*
</script>
*/