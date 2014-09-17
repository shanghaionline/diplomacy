<#assign base="/diplomacy">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content="上海公共外交协会,公共外交,外交协会"/>
    <title>上海公共外交协会 邀请新会员</title>
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
                <script type="text/javascript">
                    $(document).ready(function () {
                        $(":button#copy").bind("click", function (event) {
                            event.preventDefault();
                            copyCode("textarea");
                        });
                    });
                    function copyCode(id) {
                        var testCode = document.getElementById(id).value;
                        if (copy2Clipboard(testCode) != false) {
                            alert("生成的代码已经复制到粘贴板，你可以使用Ctrl+V 贴到需要的地方去了哦！  ");
                        }
                    }
                    copy2Clipboard = function (txt) {
                        if (window.clipboardData) {
                            window.clipboardData.clearData();
                            window.clipboardData.setData("Text", txt);
                        } else if (navigator.userAgent.indexOf("Opera") != -1) {
                            window.location = txt;
                        } else if (window.netscape) {
                            try {
                                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                            } catch (e) {
                                alert("您的firefox安全限制限制您进行剪贴板操作，请打开’about:config’将signed.applets.codebase_principal_support’设置为true’之后重试，相对路径为firefox根目录/greprefs/all.js");
                                return false;
                            }
                            var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
                            if (!clip)return;
                            var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
                            if (!trans)return;
                            trans.addDataFlavor('text/unicode');
                            var str = new Object();
                            var len = new Object();
                            var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
                            var copytext = txt;
                            str.data = copytext;
                            trans.setTransferData("text/unicode", str, copytext.length * 2);
                            var clipid = Components.interfaces.nsIClipboard;
                            if (!clip)return false;
                            clip.setData(trans, null, clipid.kGlobalClipboard);
                        }
                    }
                </script>
                <div class="login_left">
                    <div class="invite">
                        <a href="">
                            <img src="${requestContext.contextPath}/static/images/title_invite.gif" alt=""/>
                        </a>
                    </div>
                    <div class="invitestep">
                        <img src="${requestContext.contextPath}/static/images/icon_invite_1.gif" class="icon1"/>

                        <div class="invitebox">
                            <p>这是您的专用邀请链接，请通过QQ或 MSN 发送给好友：</p>
                            <textarea name="textarea" id="textarea" rows="3" class="ipt_4">我最近在上海公共外交协会网站注册了用户，您也来加入吧！http://spda.org.cn/diplomacy/user/invite/${user.id}/${checksum}</textarea>
                            <input type="button" name="copy" id="copy" value="" class="btn_copy"/>
                        </div>
                    </div>
                    <!--
                    <div class="invitestep1">
                        <img src="${requestContext.contextPath}/static/images/icon_invite_2.gif" class="icon1"/>
                        <div class="invitebox">
                            <span>分享到</span>

                            <div class="bshare-custom" style="margin-top:10px;">
                                <a title="分享到人人网" class="bshare-renren">人人网</a>
                                <a title="分享到开心网" class="bshare-kaixin001">开心网</a>
                                <a title="分享到新浪微博" class="bshare-sinaminiblog">新浪微博</a>
                                <a title="分享到腾讯微博" class="bshare-qqmb">腾讯微博</a>
                                <a title="分享到豆瓣" class="bshare-douban">豆瓣</a>
                                <a title="更多平台" id="bshare-more-icon" class="bshare-more">更多...</a>
                            </div>
                            <script type="text/javascript" charset="utf-8"
                                    src="http://static.bshare.cn/b/button.js#uuid=d9aa06f1-4ddd-4ee2-aa99-2e5005851b88&style=-1"></script>
                            <script type="text/javascript" charset="utf-8"
                                    src="http://static.bshare.cn/b/bshareC1.js"></script>
                            <script type="text/javascript">
                                bShare.addEntry({
                                    title: "上海公共外交协会",
                                    summary: "我最近在上海公共外交协会网站注册了用户，您也来加入吧！",
                                    url: "http://spda.org.cn/association/user/invite/2177/3O730xF7+L1nSJGcj0wfaQ=="
                                });
                            </script>
                        </div>
                    </div>
                    -->
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
  

