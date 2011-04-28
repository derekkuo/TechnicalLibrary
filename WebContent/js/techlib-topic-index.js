/*
<script type="text/javascript">
 */
	$(function(){
		$("#mytable").tablesorter( {sortList: [[0,0], [1,0]]} );
		$("#gathertable").tablesorter( {sortList: [[0,0], [1,0]]} );
		senfe("mytable","#ffffff","#f8fbfc","#f0f0f0","#f0f0f0");
	});

	function senfe(o,a,b,c,d){
		//senfe("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景");
		//senfe("mytable","#f8fbfc","#e5f1f4","#ecfbd4","#bce774");
		 var t=document.getElementById(o).getElementsByTagName("tr");
		 for(var i=0;i<t.length;i++){
		  t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
		  t[i].onclick=function(){
		   if(this.x!="1"){
		    this.x="1";
		    this.style.backgroundColor=d;
		   }else{
		    this.x="0";
		    this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
		   }
		  }
		  t[i].onmouseover=function(){
		   if(this.x!="1")this.style.backgroundColor=c;
		  }
		  t[i].onmouseout=function(){
		   if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
		  }
		 }
		}
/*
</script>
*/