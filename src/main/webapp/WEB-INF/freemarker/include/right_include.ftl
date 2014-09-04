<div class="login_right">
  <div class="user_info">
    <p>用户${user.login}，欢迎您登录</p>
    <input type="submit" 
	   name="button" 
	   id="button" 
	   value="退出" 
	   class="btn_logout"
	   onClick="window.location.href='/association/mangr/logout?back=%2Fassociation%2Fdiplomacy%2Fsite%2Findex'"/>
  </div>
  <div class="interactive">
    <a href="/association/diplomacy/help"><h1>站内互动</h1></a>
    <div class="interactive_links">
      <a href="/association/mangr"><img src="${requestContext.contextPath}/static/images/btn_right_0.gif" alt=""/></a>
      <a href="/association/msg/msgto/%7B%22receiver%22:%22admin%22%7D"><img src="${requestContext.contextPath}/static/images/btn_right_1.gif" alt=""/></a>
      <a href="/association/msg/msgto/%7B%22receiver%22:%22admin%22%7D"><img src="${requestContext.contextPath}/static/images/btn_right_2.gif" alt=""/></a>
      <a href="${requestContext.contextPath}/user/get-invite"><img src="${requestContext.contextPath}/static/images/btn_right_3.gif" alt=""/></a>
      <a href="/association/msg/list-receive/1"><img src="${requestContext.contextPath}/static/images/btn_right_4.gif" alt=""/></a>
      <a href="${requestContext.contextPath}/user/modify"><img src="${requestContext.contextPath}/static/images/btn_right_5.gif" alt=""/></a>
    </div>
  </div>
 </div>
 