/*
<script type="text/javascript">
 */
	$(function(){
		var removeAD = false;//是否去除广告
		var topDirectIndex = 3;//<h3>章、节上添加TOP  <h4>章、节、点上添加TOP
		
		
		//去除广告
		if(removeAD){
			var $ad = $(".ad");
			$ad.remove();
		}
		
		//添加可搜索文字的链接
		var $search = $(".search");
		$search.each(function(i){
			$(this).attr("href","search?"+$(this).text());
		});
		
		//生成文章末尾的NOTE部分BEGIN
		var $notes = $(".note");
		var $blockNote = $("#techlib-topic-notelist");
		if( $notes.length > 0){
			$blockNote.append("<a name='notelist'></a>");
			$blockNote.append("<span class='menu'><h2>NOTE LIST</h2></span>");
		}
		$notes.each(function(i){
			j=i+1;
			$blockNote.append("<div style='margin-bottom:10px;'>NOTE&nbsp;"+ j + ":&nbsp;"+$(this).html()+"</div>");
		});
		var $blockNoteHeads = $("#techlib-topic-notelist > div > .note-head");
		//alert($blockNoteHeads.length);
		$blockNoteHeads.each(function(i){
			$(this).removeAttr("class");
			//$(this).css("font-weight","bold");
			$(this).append("<br>");
		});
	
		//生成没有级别的目录
		/*
		var $headers = $(":header");
		$headers.each(function(i){
			alert($(this).html());
		});*/
		//生成目录
		var $menus = $(".menu");
		var $menubarBlock = $("#techlib-topic-rightbar");
		$menubarBlock.prepend("<div id='menubar'><div style='font-size:16px\; font-weight:bold\; margin-top:15px\; margin-bottom:15px\;'>目录</div><UL class='menubar-ul'></UL></div>");
		var $menubarUl = $(".menubar-ul");
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
	});
/*
</script>
*/