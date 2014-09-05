<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content="上海公共外交协会,公共外交,外交协会"/>
    <title>上海公共外交协会 修改密码</title>
    <link rel="stylesheet" media="screen"
          href="${requestContext.contextPath}/static/stylesheets/main.css">
    <link rel="shortcut icon" type="image/png"
          href="${requestContext.contextPath}/static/images/favicon.png">
    <link rel="stylesheet" media="screen"
          href="${requestContext.contextPath}/static/stylesheets/style.css">
    <link rel="stylesheet" media="screen"
          href="${requestContext.contextPath}/static/stylesheets/article.css">
    <script type="text/javascript"
            src="${requestContext.contextPath}/static/javascripts/jquery-1.9.0.min.js">
    </script>
</head>
<body>
<div class="bg">
    <div class="page">
        <div class="head">
            <div class="flash">
            </div>
        </div>
    <#include "../include/top_include.ftl"/>
        <div class="content">
            <div class="main">
                <div class="login_left">
                    <form action="${requestContext.contextPath}/user/modifypwd" method="POST">
                        <table width="100%" border="0" cellpadding="2">
                            <tr>
                                <td width="35%"></td>
                                <td width="12%" align="left">旧密码:</td>
                                <td>
                                    <input type="password" id="password"
                                           name="oldpassword"
                                           class="ipt_1"/>
                                    <span></span>
                                </td>
                            </tr>
                            <tr>
                                <td width="35%"></td>
                                <td align="left">新密码:</td>
                                <td>
                                    <input type="password" id="newPassword"
                                           name="password"
                                           class="ipt_1"/>
                                    <span>${errorMsg!}</span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">
                                    <input type="submit" id="edit" value="确定"/>
                                </td>
                            </tr>
                        </table>

                    </form>

                </div>
            <#include "../include/right_include.ftl">
            </div>
            <div class="bottom">
                <p>上海公共外交协会 版权所有 | 网络技术支持：
                    <a href="http://www.online.sh.cn" target="_blank">上海热线</a>
                </p>

                <p>
                    <a href="" target="_blank">内容纠错</a> |
                    <a href="" target="_blank">网站法律顾问</a>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>


