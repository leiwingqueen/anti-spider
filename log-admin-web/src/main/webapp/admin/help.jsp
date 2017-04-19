<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/include/hd.jsp"></jsp:include>
				<div class="span9">
					<section>
						<div class="page-header">
							<h1>帮助文档</h1>
						</div>
						<article class="help-page">
							<div id="J_helpHead" class="hd default bs-docs-example">
								<a href="#" class="J_helpSlider" id="J_helpSlider">鼠标移到上面，显示目录！</a>
								<ul id="J_helpNav" class="help-nav">
									<li>
										<dl class="clearfix">
											<dt>第一章 系统介绍</dt>
											<dd>
												<ul style="display: block; position: static;" role="menu" class="dropdown-menu">
													<li class="dropdown-submenu help-submemu">
														<a href="#a-1" tabindex="-1">工具管理</a>
														<ul class="dropdown-menu">
															<li><a href="#a-1-1">可执行工具</a></li>
															<li><a href="#a-1-2">待审核工具</a></li>
															<li><a href="#a-1-3">工具列表</a></li>
														</ul>
													</li>
													<li class="dropdown-submenu help-submemu">
														<a href="#a-2" tabindex="-1">系统日志</a>
														<ul class="dropdown-menu">
															<li><a href="#a-2-1">操作日志</a></li>
															<li><a href="#a-2-2">执行任务查询</a></li>
															<li><a href="#a-2-3">服务器执行日志查询</a></li>
															<li><a href="#a-2-4">任务文件下载</a></li>
														</ul>
													</li>
													<li class="dropdown-submenu help-submemu">
														<a href="#a-3" tabindex="-1">参数配置</a>
														<ul class="dropdown-menu">
															<li><a href="#a-3-1">字典参数设置</a></li>
															<li><a href="#a-3-2">工具分类</a></li>
															<li><a href="#a-3-3">终止任务管理</a></li>
														</ul>
													</li>

												</ul>
											</dd>
										</dl>
									</li>
									<li>
										<dl class="clearfix">
											<dt>第二章 系统应用</dt>
											<dd>
												<ul style="display: block; position: static;" role="menu" class="dropdown-menu">
													<li class="dropdown-submenu help-submemu">
														<a href="#b-1" tabindex="-1">工具新建</a>
														<ul class="dropdown-menu">
															<li><a href="#b-1-1">工具名称</a></li>
															<li><a href="#b-1-2">主脚本</a></li>
															<li><a href="#b-1-3">主脚本类型</a></li>
															<li><a href="#b-1-4">脚本执行类型</a></li>
															<li><a href="#b-1-5">工具分类</a></li>
															<li><a href="#b-1-6">工具说明</a></li>
															<li><a href="#b-1-7">文件上传</a></li>
															<li><a href="#b-1-8">拖拽控件</a></li>
															<li><a href="#b-1-9">编辑结果表</a></li>
														</ul>
													</li>
													<li class="help-submemu">
														<a href="#b-2" tabindex="-1">工具调试</a>
													</li>
													<li class="help-submemu">
														<a href="#b-3" tabindex="-1">工具提交</a>
													</li>
													<li class="help-submemu">
														<a href="#b-4" tabindex="-1">工具审核</a>
													</li>
													<li class="help-submemu">
														<a href="#b-5" tabindex="-1">工具使用</a>
													</li>
													<li class="help-submemu">
														<a href="#b-6" tabindex="-1">工具删除</a>
													</li>
												</ul>
											</dd>
										</dl>
									</li>
									<li>
										<dl class="clearfix">
											<dt>第三章 范例</dt>
											<dd>
												<ul style="display: block; position: static;" role="menu" class="dropdown-menu">
													<li>
														<a href="#c-1" tabindex="-1">SCANDISKINFO</a>
													</li>
												</ul>
											</dd>
										</dl>
									</li>
									<li>
										<dl class="clearfix">
											<dt>第四章 其他</dt>
											<dd>
												<ul style="display: block; position: static;" role="menu" class="dropdown-menu">
													<li class="help-submemu">
														<a href="#d-1" tabindex="-1">相关脚本</a>
													</li>
													<li class="help-submemu">
														<a href="#d-2" tabindex="-1">工具平台设计文档</a>
													</li>
												</ul>
											</dd>
										</dl>
									</li>
								</ul>	
							</div>
							<div class="bd bs-docs-example">
								<h1>第一章  系统简介</h1>
								<h2 id="a-1">1.	工具管理</h2>
								<h3 id="a-1-1">1)	可执行工具</h3>
								<p class="alert alert-info">当前登录用户有权限执行的工具列表。</p>
								包括：
								<ul>
									<li>自己创建并通过超管审核的工具；</li>
									<li>其他人创建并授权予你执行权限的工具；</li>
								</ul>
								<h3 id="a-1-2">2)	待审核工具</h3>
								<div class="alert alert-block alert-info">
									<p>所有用户创建的工具，都需要通过超管审核后才可以使用。</p>
									<p>此页面为所有处于“审核”状态的工具列表，包括自己创建的和其他人创建的。</p>
								</div>
								<h3 id="a-1-3">3)	工具列表</h3>
								<div class="alert alert-block alert-info">
									<p>所有工具的详细列表。</p>
									<p>说明：<span class="em">默认只显示自己创建的工具列表。</span> 可通过页面上的复选框改变其默认行为。</p>
								</div>
								<p>包括：</p>
								<ul>
									<li>自己创建的工具；</li>
									<li>别人创建的工具；</li>
								</ul>
								<h2 id="a-2">2.	系统日志</h2>
								<h3 id="a-2-1">1)	操作日志</h3>
								<p class="alert alert-info">工具操作日志。包括增、删、改、使用记录等。</p>
								<h3 id="a-2-2">2)	执行任务查询</h3>
								<p class="alert alert-info">用户工具的执行任务查询。包括当前运行任务以及历史任务。查询出结果后可以查看相应任务的执行状态、结果等相关信息。</p>
								<h3 id="a-2-3">3)	服务器执行日志查询</h3>
								<p class="alert alert-info">主要是执行结果的返回信息查询。</p>
								<h3 id="a-2-4">4)	任务文件下载</h3>
								<div class="alert alert-block alert-info">
									<p>多用于用户DEBUG。</p>
									<p>当用户定义的工具未能如愿完成相应工作时，可下载工具系统打包生成的相应任务文件（tar.gz包）进行调试。</p>
								</div>
								<p>任务文件内包含：</p>
								<ul>
									<li>用户上传的工具脚本；</li>
									<li>用户执行工具所提交的参数，参数配置所生成的参数配置文件</li>
								</ul>
								<h2 id="a-3">3.	参数配置</h2>
								<h3 id="a-3-1">1)	字典参数设置</h3> 
								<p class="alert alert-info">待完善</p>
								<h3 id="a-3-2">2)	工具分类</h3>
								<p class="alert alert-info">工具的分类列表，可以新增工具类别。（如新增一个“机房下线工具”）</p>
								对于当前各类别工具的说明，如：
								<ul>
									<li>专注于系统维护的“系统工具”；</li>
									<li>专注于mysql数据库维护的“mysql工具”；</li>
									<li>为便利值班同事进行值班工作而创建的“值班工具”；</li>
								</ul>
								<h3 id="a-3-3">3)	终止任务管理</h3>
								<div class="alert alert-block alert-info">
									<p>当用户提交了一个任务，突然发现需要变更或需要紧急停止此任务时，可以通过此功能进行任务的终止。</p>
									<p>如：下发了一个全网lsof指令，突然想到lsof会导致系统负载高，需要临时终止任务，此时可通过此功能紧急终止任务的执行。</p>
								</div>
								<h1>第二章  系统应用</h1>
								<h2 id="b-1">1.	工具新建</h2>
								<p>执行步骤：<span class="em">“工具管理”-“工具列表”-“新建”- 输入相应信息</span>，如下：</p>
								<div class="helpImg">
									<img src="/img/help/pic1.png" alt="" />
								</div>
								<h3 id="b-1-1">1)	工具名称</h3>
								<p class="alert alert-info">顾名思义，用户创建工具的名称，可以为中文、英文。</p>
								<h3 id="b-1-2">2)	主脚本</h3>
								<p class="alert alert-info">工具执行的入口，类似controler的功能。用户可以上传多个脚本，但必定有一个是作为工具执行入口的主脚本，由主脚本独自完成，或调用其他脚本/文件完成相应功能。</p>
								<h3 id="b-1-3">3)	主脚本类型</h3>
								<p class="alert alert-info">当前支持SHELL及PYTHON脚本，PHP已经并入开发周期。有其他需求可以协商。</p>
								<h3 id="b-1-4">4)	脚本执行类型</h3>
								<div class="alert alert-block alert-info">
									当前支持两种模式：
									<p>远程：通过工具系统将指定任务下发到相应IP地址的操作系统，并通过<code>TOOLS AGENT</code>触发用户定义的脚本完成相应功能；</p>
									<p>管理机：通过工具系统将指定任务下发到管理机，通过管理机完成相应任务。</p>
								</div>
								<h3 id="b-1-5">5)	工具分类</h3>
								<p class="alert alert-info">用户通过<span class="em">“参数配置”</span>定义的工具分类。不同工具分类用于完成不同功能。</p>
								<h3 id="b-1-6">6)	工具说明</h3>
								<p class="alert alert-info">用户为工具所添加的备注，以方便自己或者是他人更好的了解使用此工具。</p>
								<p>“下一步”，进入工具定义页面。</p>
								<h3 id="b-1-7">7)	文件上传</h3>
								<div class="helpImg">
									<img src="/img/help/pic2.png" alt="" />
								</div>
								<div class="alert alert-block alert-info">
									<p>上传写好的脚本（PYTHON,SHELL,PHP），可以为多个。但主脚本必须是“工具信息”里面定义的主脚本。</p>
									<p>如：我上传<code>commonFunction.sh</code>，<code>scanProcess.sh</code>两个脚本（<a href="#d-1">参照：附件</a>），其中<code>scanProcess.sh</code>是完成实际功能的脚本，</p>
									<p>而 <code>commonFunction.sh</code> 是供<code>scanProcess.sh</code>调用的公共函数库。</p>
								</div>
								<h3 id="b-1-8">8)	拖拽控件</h3>
								<div class="helpImg">
									<img src="/img/help/pic3.png" alt="" />
								</div>
								<p class="alert alert-info">拖拽控件专用于脚本的“传参”功能，即可以将工具定义为标准化工具，通过用户提交的不同参数，完成类似的功能。</p>
								<p><span class="label label-important">注意</span>：用户提交的所有参数内容都会以<code>key=value</code>的方式存入<code>para.config</code>，<code>para.conf</code>与用户上传的脚本将下发到同一个目录内，所以用户脚本只需要读取<code>./para.config</code>内容进行参数获取分析即可。</p>
								<p>下面是我写的一个参数处理的公共函数，仅供参考（<a href="#d-1">参照：附件</a>）：</p>
								<div class="bs-docs-example">
									<pre>
#------------------------#
# 脚本参数相关信息       #
#------------------------#

# 脚本参数解析（全局）
function parseParameter {

    # 将\n替换成\\n，避免echo "$PARA_STRING"时直接就将\干掉了，变成n
    
    sed -i 's#\\n#\\\\n#g' para.config
    
    while read PARA_STRING ; do

        # 判断参数是否符合key=value格式
        
        if ! echo "$PARA_STRING" | egrep -q '^[^ =]+=[^ =]+.*$' ; then

            code=52 ; msg="$FUNCNAME [ERR ] | invalid format \"$PARA_STRING\", it should be in key=value format"

            printResult "$code" "$msg"

            return $code
             
        else
        
            # 遍历定义每一个参数
            
            # 参数名获取           
            PARA_NAME=$( echo "$PARA_STRING" | sed -r 's#(.*)=(.*)#\1#g' )
            
            # 参数值获取           
            PARA_VALUE=$( echo "$PARA_STRING" | sed -r 's#(.*)=(.*)#\2#g' )
            
            # 参数名，参数值对应赋值
            eval "$PARA_NAME=\"$PARA_VALUE\"" 2>/dev/null || { code=53 ; msg="$FUNCNAME [ERR ] | eval error"

            printResult "$code" "$msg"

            return $code; }
             
        fi

    done < para.config

}

									</pre>
								</div>
								<h4>拖拽空间又包含了几种形式：文本框、文本域、单选框（开发中）、复选框。下面分别举例说明：</h4>
								<h5>8.1 文本框：</h5>
								<p class="alert alert-info">用户参数提交、获取的定义区。</p>
								<p>如：
									<code>scanProcess.sh</code>脚本（<a href="#d-1">参照：附件</a>）的功能是获取用户输入的进程名称，并在相应SERVER上搜索是否存在此进程。
									<br>
									若存在，输出此进程相关信息；
									<br>
									若不存在，输入<code>not found</code>；
									<br>
									此时我们的文本框可以定义为（<span class="em">若存在多个参数，则定义多个文本框</span>）：
								</p>
								<div class="helpImg">
									<img src="/img/help/pic4.png" alt="" />
									<img src="/img/help/pic5.png" alt="" />
								</div>
								<span class="label label-info">脚本内对应的参数内容为：</span>
								<div class="bs-docs-example">
									<pre>
# 进程名称

# PARA_PROCESS_NAME

if [ -z "$PARA_PROCESS_NAME" ];then

    code=102; msg="$SCRIPT_NAME [ERR ] | PARA_PROCESS_NAME value is null."

    printResult "$code" "$msg" 

    exit $code
    
fi
									</pre>
								</div>
								<h5>8.2 文本域：</h5>
								<p class="alert alert-info">参照“文本框”说明，不同的是“文本框”是将用户提交的某个值赋予某个变量，“文本域”是将用户提交的一段信息（甚至可以是一篇文章）赋值为某变量。如：
用户告警信息过滤的工具<code>grepAlertInfo</code>（<a href="#d-1">参照：附件</a>）的文本域：</p>
								<div class="helpImg">
									<img src="/img/help/pic6.png" alt="" />
									<img src="/img/help/pic7.png" alt="" />
								</div>
								<h5>8.3 单选框：</h5>
								<p class="alert alert-info">Radio模式的单选框，N个条件只能选一。（<span class="em">待完善</span>）</p>
								<h5>8.4 复选框：</h5>
								<p class="alert alert-info">Checkbox模式的复选框，可以同时选择N种条件。</p>
								<p>如：获取服务器信息的工具<code>serverInfo</code>（<a href="#d-1">参照：附件</a>）里面的定义：</p>
								<div class="helpImg">
									<img src="/img/help/pic8.png" alt="" />
									<img src="/img/help/pic9.png" alt="" />
								</div>
								<h3 id="b-1-9">9)	编辑结果表</h3>
								<p class="alert alert-info">用户信息收集结果表：将工具脚本在SERVER上执行的相关信息收集并反馈上来。</p>
								<p>如：执行磁盘readonly检测以及磁盘空间信息的工具<code>scanDiskInfo</code>（<a href="#d-1">参照：附件</a>），其结果表定义如下：</p>
								<p>注意：用户输出的列与列之间，<span class="em">必须以<code>/t</code>分割</span>，否则无法正确收集反馈信息。参照下图：</p>
								<div class="helpImg">
									<img src="/img/help/pic10.png" alt="" />
								</div>
								<p><span class="em">后端脚本需要严格对应输出信息，否则结果无法正确收集上来。</span>脚本内输入定义如下（特别注意 红色/黄色 标注部分与编辑结果表参数的对应）：</p>
								<div class="bs-docs-example">
<pre># 循环取出分区相关数据
	for PARTITION in $(cat /etc/mtab |grep ^/dev |awk '{print $2}');do
	
	
	    # 获取磁盘空间信息
	    DISK_SPACE=$(df -lh|grep -w "$PARTITION"$|tr -d \%|awk '{print $NF,$(NF-1)}'|awk '{if($2>'"${PARA_THRESHOLD}"') print}')
	    
	    if [ "$DISK_SPACE" != "" ];then
	    
	        DISK_SPACE_STRING=$(echo "$DISK_SPACE"|sed -r 's/ +/\t/g')
	        echo "DISK_SPACE\t$IP_ADDR\t$DISK_SPACE_STRING%"
	        
	    fi
	
	    
	    # 判断磁盘是否readonly状态
	    TEMP_FILE=$(mktemp -p $PARTITION) && { rm -f $TEMP_FILE ; echo "DISK_STATUS\t$IP_ADDR\t$PARTITION\tok" ; } || echo "DISK_STATUS\t$IP_ADDR\t$PARTITION\treadonly"
	  
	   
	done</pre>

								</div>
								<p>我们执行此工具，再看一下其反馈的结果表信息。</p>
								<div class="helpImg">
									<img src="/img/help/pic11.png" alt="" />
									<img src="/img/help/pic12.png" alt="" />
									<img src="/img/help/pic13.png" alt="" />
									<img src="/img/help/pic14.png" alt="" />
								</div>
								<h3 id="b-2">2.	工具调试</h3>
								<div class="alert alert-block alert-info">
									<p>工具创建后需要经过调试，确保可以正常运行后，才能进行发布使用。</p>
									<p>自己创建的工具，创建完毕后可以通过<span class="em">“待审核工具”-“审核”-“试运行”</span>进行调试。</p>
									<p>若工具执行不正常，可以前往“<code>任务文件下载</code>”，将工具系统打包后的工具下载下来进行调试。</p>
								</div>
								<div class="helpImg">
									<img src="/img/help/pic15.png" alt="" />
								</div>
								<p>下载并解压后（可以将解压后的文件放到服务器山进行调试）：</p>
								<div class="helpImg">
									<img src="/img/help/pic16.png" alt="" />
								</div>
								<h3 id="b-3">3.	工具提交</h3>
								<p class="alert alert-info">工具创建后，需要提交给管理员进行审核后方可供自己/他人使用。</p>
								<h3 id="b-4">4.	工具审核</h3>
								<p class="alert alert-info">管理员对相关的工具进行审核操作。（<a href="#">参照：待审核工具</a>）</p>
								<h3 id="b-5">5.	工具使用</h3>
								<p class="alert alert-info">使用工具完成相关功能。</p>
								<h3 id="b-6">6.	工具删除</h3>
								<p class="alert alert-info">删除不再使用的工具。</p>
								<h1>第三章  范例</h1>
								<h2 id="c-1">1.	ScanDiskInfo</h2>
								<div class="helpImg">
									<img src="/img/help/pic17.png" alt="" />
									<img src="/img/help/pic18.png" alt="" />
									<img src="/img/help/pic19.png" alt="" />
									<img src="/img/help/pic20.png" alt="" />
									<img src="/img/help/pic21.png" alt="" />
									<img src="/img/help/pic22.png" alt="" />
									<img src="/img/help/pic23.png" alt="" />
								</div>
								<h1>第四章  其他</h1>
								<h2 id="d-1">1)	相关脚本</h2>
								<ul>
									<li><a href="/img/help/ssh/commonFunction.sh">commonFunction.sh</a></li>
									<li><a href="/img/help/ssh/grepAlertInfo.sh">grepAlertInfo.sh</a></li>
									<li><a href="/img/help/ssh/operateServer.sh">operateServer.sh</a></li>
									<li><a href="/img/help/ssh/scanProcess.sh">scanProcess.sh</a></li>
									<li><a href="/img/help/ssh/serverInfo.sh">serverInfo.sh</a></li>
								</ul>
								<h2 id="d-2">2)	工具平台设计文档</h2>
								<ul>
									<li><a href="/img/help/工具系统使用说明（初稿）.docx">工具系统使用说明（初稿）.docx</a></li>
								</ul>
							</div>
						</article>
					</section>
				</div>
			</div>
		</div>
	<jsp:include page="/include/ft.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
			var J_helpSlider = $('#J_helpSlider'), J_helpNav = $('#J_helpNav'), J_helpHead = $('#J_helpHead'),
				pos = J_helpHead.offset().top,
				links = $('#J_helpNav').find('a');
			J_helpHead.hover(function(){
				if(J_helpNav.is(':visible')){
					return;
				}
				J_helpNav.stop(true).slideDown();
				return false;
			},function(){
				if(!J_helpNav.is(':visible')){
					return;
				}
				J_helpNav.stop(true).slideUp();
				return false;
			})
			// 跳转
			links.each(function(){
				$(this).on('click',function(){
					var obj = $(this).attr('href').substring(1);
					J_helpSlider.click();
					$(window).scrollTop($('#'+obj).offset().top - 107);
					return false;
				})
			})
			$(window).scroll(function(){
				if($(this).scrollTop() >= pos && J_helpHead.hasClass('default')){
					J_helpHead.removeClass('default').addClass('fixed');
					J_helpNav.slideUp();
				} else if($(this).scrollTop() < pos && J_helpHead.hasClass('fixed')){
					J_helpHead.removeClass('fixed').addClass('default');
				}
				if($(this).scrollTop() == 0){
					if(!J_helpNav.is(':visible')){
						J_helpNav.stop(true).slideDown();
					}
				}
			})
		})
	</script>
	</body>
</html>
<script>

</script>	