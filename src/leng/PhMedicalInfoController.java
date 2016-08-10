/*package leng;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.frame.common.TokenInterceptor;
import com.jfinal.render.*;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.interceptor.ParamerInterceptor;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.GgwsfwService;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.StringTool;
import com.jfinal.plugin.activerecord.TimeTool;
import com.jfinal.render.ExportService;
import com.jfinal.render.JsonParser;
import com.jfinal.render.PublicUtil;
import com.jfinal.upload.UploadFile;
import com.tbyf.core.exception.BusinessException;

*//**
 * PhFieldconfigController
 * 
 *//*
@Before(ParamerInterceptor.class)
public class PhMedicalInfoController extends Controller {
	private GgwsfwService gs = new GgwsfwService();
	private GroupController gc = new GroupController();
	protected ExportServiceAll service = ExportServiceAll.service;
	
	
	public void list() {
		String whereSql = "";
		if (!xmlData.equals("")) {
			Map m = JsonParser.json2Map(xmlData);

			whereSql = whereSql(m);
		}
		String grxxid = this.getPara("grxxid");
		int pageNum = 1;
		if (this.getPara("pageNum") != null) {
			pageNum = this.getParaToInt("pageNum");
		}
		String sql = "from ph_medicalinfo d where d.ryguid='" + grxxid
				+ "' and state='0' " + whereSql + " order by tjsj DESC,id  ";
		if (this.getPara("dzid") != null) {
			sql = "from ph_medicalinfo d where state='0' and jtdzcode="
					+ this.getPara("dzid") + "  " + whereSql
					+ " order by tjsj DESC,id ";
		}
		Page p = PhMedicalInfo.dao.paginate(pageNum, 20, "select d.*", sql);
		renderJson(p);
	}

	public void getListByRyguid() {
		String ryguid = this.getPara("ryguid");
		int pageNum = 1;
		if (this.getPara("pageNum") != null) {
			pageNum = this.getParaToInt("pageNum");
		}
		String sql = "from ph_medicalinfo d where state='0' and d.ryguid = '"
				+ ryguid + "'  order by d.tjsj desc";
		Page p = PhMedicalInfo.dao.paginate(pageNum, 20, "select d.*", sql);
		renderJson(p);
	}

	public void toNewPage() {
		Map user = (Map) this.getSession().getAttribute("uerroleinfo");
		String tjbhyear =  this.getSession().getAttribute("tjbhyear").toString();
		if (user == null) {
			map.put("type", "1");
			map.put("content", "登录用户信息失效，请重新登陆！");
			renderJson(JsonParser.map2Json(map));
			return;
		}
		Map operateInfo = JsonParser.json2Map(user.get("OPERATE").toString());
		Map groupinfo = JsonParser.json2Map(user.get("ENTERPRISE").toString());
		// String groupinfo = (String) user.get("ENTERPRISE");
		if (groupinfo == null || groupinfo.equals("")) {
			map.put("type", "1");
			map.put("content", "登录用户信息失效，请重新登陆！");
			renderJson(JsonParser.map2Json(map));
			return;
		}
		java.util.Date utilDate = new Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		String tjHdsa0001guid = this.getPara("tjHdsa0001.guid");
		GrjbxxView gv = GrjbxxView.dao.findById(tjHdsa0001guid);
		PhMedicalInfo phMedicalInfo = new PhMedicalInfo();
		phMedicalInfo.set("ryguid", tjHdsa0001guid);
		phMedicalInfo.set("tjjgcode", operateInfo.get("f_groupid"));
		phMedicalInfo.set("tjjgname", groupinfo.get("groupname"));
		phMedicalInfo.set("tjrycode", operateInfo.get("usercode"));
		phMedicalInfo.set("tjryname", operateInfo.get("username"));
		phMedicalInfo.set("jtdzcode", gv.get("czdzcode"));
		phMedicalInfo.set("tjbhyear", tjbhyear);
		phMedicalInfo.set("bclx", "1");//默认保存类型为临时保存
		setAttr("ryguid", tjHdsa0001guid);
		setAttr("phMedicalInfo", phMedicalInfo);
		setAttr("grjbxx", gv);
		createToken("jockillerToken", 30*60);
		render("../pages/tjxxgl/tjxxlr.jsp");
	}

	public void toEditPage() {
		Map user = (Map) this.getSession().getAttribute("uerroleinfo");
		if (user == null) {
			throw new BusinessException("登录用户信息失效,请重新登陆");
		}
		String groupinfo = (String) user.get("ENTERPRISE");
		if (groupinfo == null || groupinfo.equals("")) {
			throw new BusinessException("登录用户信息失效,请重新登陆");
		}
		user = JsonParser.json2Map(groupinfo);
		java.util.Date utilDate = new Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		String tjxxid = this.getPara("tjxxid");
		
		PhMedicalInfo pi = PhMedicalInfo.dao.findById(tjxxid);
		GrjbxxView gv = GrjbxxView.dao.findById(pi.get("ryguid"));
		if( pi.get("tjsj")!=null&&!pi.get("tjsj").toString().equals("")&& pi.get("tjsj").toString().length()>10){
			pi.set("tjsj", pi.get("tjsj").toString().substring(0, 10));
		}
		if( pi.get("dcsj")!=null&&!pi.get("dcsj").toString().equals("")&& pi.get("dcsj").toString().length()>10){
			pi.set("dcsj", pi.get("dcsj").toString().substring(0, 10));
		}
		setAttr("ryguid", pi.get("ryguid"));
		setAttr("phMedicalInfo", pi);
		setAttr("grjbxx", gv);
		createToken("jockillerToken", 30*60);
		render("../pages/tjxxgl/tjxxlr.jsp");
	}

	@Before(value = {TokenInterceptor.class })
	public void save() {
		
		PhMedicalInfo pi = getModel(PhMedicalInfo.class);
		Map user = (Map) this.getSession().getAttribute("uerroleinfo");
		if (user == null) {
			map.put("type", "1");
			map.put("content", "登录用户信息失效，请重新登陆！");
			renderJson(JsonParser.map2Json(map));
			return;
		}
		Map groupinfo = JsonParser.json2Map(user.get("ENTERPRISE").toString());
		// String groupinfo = (String) user.get("ENTERPRISE");
		if (groupinfo == null || groupinfo.equals("")) {
			map.put("type", "1");
			map.put("content", "登录用户信息失效，请重新登陆！");
			renderJson(JsonParser.map2Json(map));
			return;
		}
		Map operateInfo = JsonParser.json2Map(user.get("OPERATE").toString());
		String operateF_groupid = operateInfo.get("f_groupid").toString();
		String operateRegioncode = groupinfo.get("f_regioncode").toString();
		String pigroupid = pi.getStr("tjjgcode");
		if(operateF_groupid!=null&&!operateF_groupid.equals("")&&pigroupid!=null&&!pigroupid.equals("")){
			if(gc.changePermissions(operateF_groupid,pigroupid,operateRegioncode)){
				TjHdsa0001 tjHdsa0001 = TjHdsa0001.dao.findById(pi.get("ryguid"));
				TjHdsa000101 tjHdsa000101 = TjHdsa000101.dao.findById(pi.get("ryguid"));
				tjHdsa0001.set("hdsa0001_001", pi.get("xm"));// 姓名
				tjHdsa0001.set("hdsa0001_008", pi.get("xb"));// 姓名
				String hdsa0001_003 = pi.getStr("sfz");
				if(hdsa0001_003!=null&&!hdsa0001_003.equals("")){
					hdsa0001_003 = hdsa0001_003!=null?hdsa0001_003.toUpperCase():null;//身份证转为大写
				}
				tjHdsa0001.set("hdsa0001_003", hdsa0001_003);// 身份证
				tjHdsa0001.set("hdsa0001_010", pi.get("csrq"));// 出生日期
				tjHdsa0001.set("hdsa0001_033", pi.get("xxdz"));// 家庭地址
				tjHdsa0001.set("hdsa0001_029", pi.get("gddh"));// 固话
				tjHdsa0001.update();
				tjHdsa000101.set("hdsa000101_021", pi.get("hyzk"));// 婚姻状况
				tjHdsa000101.set("hdsa000101_017", pi.get("whcd"));// 文化程度
				tjHdsa000101.set("jzjbs", pi.get("jzjbs"));// 家庭疾病史
				tjHdsa000101.set("jzjbsqt", pi.get("jzjbsqt"));// 家族疾病史其他
				tjHdsa000101.set("hdsa000101_024", pi.get("gms"));// 过敏史
				tjHdsa000101.set("gmsyw", pi.get("gmsyw"));// 过敏史药物
				tjHdsa000101.set("gmssw", pi.get("gmssw"));// 过敏史食物
				tjHdsa000101.set("gmsqt", pi.get("gmsqt"));// 过敏史其他
				tjHdsa000101.set("hdsa000101_005", pi.get("yddh"));// 手机
				tjHdsa000101.set("hdsa000101_006", pi.get("jsxm"));// 家属姓名
				tjHdsa000101.set("hdsa000101_007", pi.get("lxfs"));// 联系方式
				tjHdsa000101.update();
				if (user == null) {
					map.put("type", "1");
					map.put("content", "登录用户信息失效，请重新登陆！");
					renderJson(JsonParser.map2Json(map));
					return;
				}
				try {
					if (pi.get("id") == null) {
						pi.set("id", PublicUtil.getUUID());
						java.util.Date utilDate = new Date();
						java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

						pi.set("xgsj", sqlDate);
						pi.set("lrsj", sqlDate);
						pi.save();
					} else {
						java.util.Date utilDate = new Date();
						java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
						pi.set("xgsj", sqlDate);
						pi.update();
					}
					gzjdbj(pi);
					GrjbxxView gv = GrjbxxView.dao.findById(pi.get("ryguid"));
					pi.set("tjsj", pi.get("tjsj").toString().substring(0, 10));
					setAttr("phMedicalInfo", pi);
					setAttr("ryguid", pi.get("ryguid"));
					setAttr("grjbxx", gv);
					// redirect("/pages/tjxxgl/tjxxlr.jsp");
					if(pi.get("id")!=null&&!pi.getStr("id").equals("")){
						setAttr("detail", "操作成功！");
					}else{
						setAttr("detail", "操作成功！");
					}
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					GrjbxxView gv = GrjbxxView.dao.findById(pi.get("ryguid"));
					pi.set("tjsj", pi.get("tjsj").toString().substring(0, 10));
					pi.set("id", "");
					pi.set("tjjgcode", operateInfo.get("f_groupid"));
					pi.set("tjjgname", groupinfo.get("groupname"));
					pi.set("tjrycode", operateInfo.get("usercode"));
					pi.set("tjryname", operateInfo.get("username"));
					pi.set("jtdzcode", gv.get("czdzcode"));
					setAttr("phMedicalInfo", pi);
					setAttr("ryguid", pi.get("ryguid"));
					setAttr("grjbxx", gv);
					// redirect("/pages/tjxxgl/tjxxlr.jsp");
					setAttr("phMedicalInfo", pi);
					String catchstr = e.getMessage().replace("\"", "").replace(")", "")
							.replace("\n", "");
					catchstr = catchstr
							.substring(catchstr.indexOf("PH_MEDICALINFO") + 15);
					System.out.println("======" + catchstr);
					setAttr("detail", "您还有项目未完成:<br>"
							+ pi.getFieldName(catchstr.toLowerCase()));
					e.printStackTrace();
				}
			}else{
				GrjbxxView gv = GrjbxxView.dao.findById(pi.get("ryguid"));
				setAttr("grjbxx", gv);
				setAttr("phMedicalInfo", pi);
				setAttr("ryguid", pi.get("ryguid"));
				setAttr("detail", "保存失败，不能修改其他机构数据！");
				
			}
		}else{
			GrjbxxView gv = GrjbxxView.dao.findById(pi.get("ryguid"));
			setAttr("grjbxx", gv);
			setAttr("phMedicalInfo", pi);
			setAttr("ryguid", pi.get("ryguid"));
			setAttr("detail", "保存失败，请联系管理员！");
		}
		createToken("jockillerToken", 30*60);
		render("../pages/tjxxgl/tjxxlr.jsp");
		// renderJson("{success:1}");
		
	}

	public void delete() {
		Map user = (Map) this.getSession().getAttribute("uerroleinfo");
		if (user == null) {
			map.put("type", "1");
			map.put("content", "登录用户信息失效，请重新登陆！");
			renderJson(JsonParser.map2Json(map));
			return;
		}
		Map operateInfo = JsonParser.json2Map(user.get("OPERATE").toString());
		Map groupinfo = JsonParser.json2Map(user.get("ENTERPRISE").toString());
		// String groupinfo = (String) user.get("ENTERPRISE");
		if (groupinfo == null || groupinfo.equals("")) {
			map.put("type", "1");
			map.put("content", "登录用户信息失效，请重新登陆！");
			renderJson(JsonParser.map2Json(map));
			return;
		}
		String operateF_groupid = operateInfo.get("f_groupid").toString();
		String operateRegioncode = groupinfo.get("f_regioncode").toString();
		String tjxxid = this.getPara("tjxxid");
		PhMedicalInfo pi = PhMedicalInfo.dao.findById(tjxxid);
		String tjbh_TjGZJDCX = pi.getStr("tjbhyear")+pi.getStr("tjbh");
		String tjid = pi.getStr("id");
		TjGZJDCX tjold = TjGZJDCX.dao.findFirst("select d.* from tj_gzjdcx d where tjid = '"+tjid+"'");
		String pigroupid = pi.getStr("tjjgcode");
		if(operateF_groupid!=null&&!operateF_groupid.equals("")&&pigroupid!=null&&!pigroupid.equals("")){
			if(gc.changePermissions(operateF_groupid,pigroupid,operateRegioncode)){
				String tjbh = pi.getStr("tjbh")+"_del";
				pi.set("tjbh", tjbh).set("state", "1").update();
				tjold.set("tjbh", tjbh_TjGZJDCX+"_del").set("state", "1").update();
				map.put("content", "操作成功！");
			}else{
				map.put("content", "不能删除其他机构数据！");
			}
		}else{
			map.put("content", "删除失败，请联系管理员！");
		}
		renderJson(JsonParser.map2Json(map));

	}

	public String whereSql(Map m) {
		String xm = (String) m.get("xm");
		String sfzh = (String) m.get("sfzh");
		String rq1 = (String) m.get("rq1");
		String rq2 = (String) m.get("rq2");
		StringBuffer sb = new StringBuffer();
		if (!xm.equals("")) {
			sb.append(" and xm='" + xm + "'");
		}
		if (!sfzh.equals("")) {
			sb.append(" and sfz='" + sfzh.toLowerCase() + "'");
		}

		return sb.toString();
	}
	public String getF_regioncode(String groupid){
		return Group.dao.findById(groupid).getStr("f_regioncode");
	}
	
	public void gzjdbj(PhMedicalInfo pi) {
		String lrdwRegionCode =  this.getF_regioncode(pi.getStr("tjjgcode"));
		String wjdc = "0";
		String ybjc = "0";
		String rzyd = "0";
		String nk = "0";
		String wk = "0";
		String yktl = "0";
		String kqk = "0";
		String xyjc = "0";
		String nyjc = "0";
		String xdt = "0";
		String xg = "0";
		String bc = "0";
		String gmd = "0";
		String jbxx = "0";
		jbxx = "1";
		if (pi.get("question1") != null && pi.get("question2") != null
				&& pi.get("question3") != null && pi.get("question4") != null
				&& pi.get("question5") != null && pi.get("question6") != null
				&& pi.get("question7") != null && pi.get("question8") != null
				&& pi.get("question9_1") != null
				&& pi.get("question9_2") != null
				&& pi.get("question9_3") != null
				&& pi.get("question9_4") != null
				&& pi.get("question9_5") != null
				&& pi.get("question10") != null && pi.get("question11") != null
				&& pi.get("question12") != null && pi.get("question13") != null
				&& pi.get("question14") != null) {
			wjdc = "1";
		}
		if (pi.get("xyssy") != null && pi.get("xyszy") != null
				&& pi.get("sg") != null && pi.get("tz") != null
				&& pi.get("yw") != null && pi.get("xybj") != null
				&& pi.get("bmi") != null) {
			ybjc = "1";
		}
		if (pi.get("rzgnwp1") != null && pi.get("rzgnwp2") != null
				&& pi.get("rzgnwp3") != null && pi.get("rzgn2") != null
				&& pi.get("rzgn3") != null && pi.get("qgztcc") != null
				&& pi.get("ydnljc1") != null && pi.get("ydnljc2") != null
				&& pi.get("ydnljc3") != null && pi.get("phnlcs") != null) {
			rzyd = "1";
		}
		if (pi.get("xlcs") != null && pi.get("xl") != null
				&& pi.get("zy") != null && pi.get("sftzx") != null
				&& pi.get("hxy") != null && pi.get("ly") != null
				&& pi.get("fbyt") != null && pi.get("fbbk") != null
				&& pi.get("gd") != null && pi.get("pd") != null
				&& pi.get("fbydxzy") != null) {
			nk = "1";
		}
		if (pi.get("pf") != null && pi.get("qblbj") != null
				&& pi.get("xzsz") != null && pi.get("zbdmbd") != null) {
			wk = "1";
		}
		if (((pi.get("lysll") != null && pi.get("lyslr") != null) || (pi
				.get("jzsll") != null && pi.get("jzslr") != null))
				&& pi.get("gm") != null && pi.get("tl") != null) {
			yktl = "1";
		}
		if (pi.get("kc") != null && pi.get("cl") != null
				&& pi.get("yb") != null) {
			kqk = "1";
		}
		if (pi.get("xdtjcjl") != null) {
			if(pi.getStr("xdtjcjl")!=null&&!pi.getStr("xdtjcjl").equals("3")){
				xdt = "1";
			}
		}
		if (pi.get("xbxgsyjcjl") != null) {
			if(pi.getStr("xbxgsyjcjl")!=null&&!pi.getStr("xbxgsyjcjl").equals("3")){
				xg = "1";
			}
		}
		if (pi.get("fbhbbcjcjl") != null) {
			if(pi.getStr("fbhbbcjcjl")!=null&&!pi.getStr("fbhbbcjcjl").equals("3")){
				bc = "1";
			}
		}
		if (pi.get("gmdjcjg") != null) {
			if(pi.getStr("gmdjcjg")!=null&&!pi.getStr("gmdjcjg").equals("4")){
				gmd = "1";
			}
		}
		
		if (pi.get("ptt") != null && pi.get("bxb") != null
				&& pi.get("dbz") != null && pi.get("nqx") != null
				&& pi.get("yxsy") != null && pi.get("ndy") != null
				&& pi.get("dhs") != null && pi.get("ntt") != null
				&& pi.get("phz") != null && pi.get("bz") != null) {
			nyjc = "1";
		}
		if (pi.get("kfxt") != null && pi.get("kfxtzsz") != null
				&& pi.get("gysz") != null && pi.get("gyszzsz") != null
				&& pi.get("zdgc") != null && pi.get("zdgczsz") != null
				&& pi.get("gmdzdgc") != null && pi.get("gmdzdgczsz") != null
				&& pi.get("dmdzdgc") != null && pi.get("dmdzdgczsz") != null
				&& pi.get("xqbaszam") != null && pi.get("xqbaszamzsz") != null
				&& pi.get("xqtdaszam") != null && pi.get("xqtdaszamzsz") != null
				&& pi.get("zdhs") != null && pi.get("zdhszsz") != null
				&& pi.get("xqzdb") != null && pi.get("xqzdbzsz") != null
				&& pi.get("bdb") != null && pi.get("bdbzsz") != null
				&& pi.get("qdb") != null && pi.get("qdbzsz") != null
				&& pi.get("xqjg") != null && pi.get("xqjgzsz") != null
				&& pi.get("xnsd") != null && pi.get("xnsdzsz") != null
				&& pi.get("xqns") != null && pi.get("xqnszsz") != null
				&& pi.get("bxbjs") != null && pi.get("bxbjszsz") != null
				&& pi.get("hxbjs") != null && pi.get("hxbjszsz") != null
				&& pi.get("xhdb") != null && pi.get("xhdbzsz") != null
				&& pi.get("xxbjs") != null && pi.get("xxbjszsz") != null
				&& pi.get("hxbyj") != null && pi.get("hxbyjzsz") != null ) {
			xyjc = "1";
		}
		
		String tjbh = pi.getStr("tjbhyear")+pi.getStr("tjbh");
		String tjid = pi.getStr("id");
		if(tjbh!=null&&!tjbh.equals("")){
			TjGZJDCX tjold = TjGZJDCX.dao.findFirst("select d.* from tj_gzjdcx d where tjid = '"+tjid+"'");
			if(tjold==null){
				TjGZJDCX tj = new TjGZJDCX();
				tj.set("guid", PublicUtil.getUUID());
				tj.set("xm", pi.get("xm"));
				tj.set("sfz", pi.get("sfz"));
				tj.set("tjbh", tjbh );
				tj.set("tjsj", pi.get("tjsj"));
				tj.set("jddw", pi.get("tjjgcode"));
				tj.set("lrdw", pi.get("tjjgcode"));
				tj.set("lrry", pi.get("tjrycode"));
				tj.set("zrys", pi.get("tjrycode"));
				tj.set("wjdc", wjdc);
				tj.set("ybjc", ybjc);
				tj.set("rzyd", rzyd);
				tj.set("nk", nk);
				tj.set("wk", wk);
				tj.set("yktj", yktl);
				tj.set("kqk", kqk);
				tj.set("xyjc", xyjc);
				tj.set("nyjc", nyjc);
				tj.set("xdt", xdt);
				tj.set("xg", xg);
				tj.set("bc", bc);
				tj.set("gmd", gmd);
				tj.set("czdz", pi.get("jtdzcode"));
				tj.set("lrdwregioncode", lrdwRegionCode);
				tj.set("bclx", pi.get("bclx"));
				tj.set("state", pi.get("state"));
				tj.set("tjid", tjid);
				tj.set("jddwname", pi.get("tjjgname"));
				tj.set("lrdwname", pi.get("tjjgname"));
				tj.set("lrryname", pi.get("tjryname"));
				tj.set("zrysname", pi.get("tjryname"));
				tj.set("jbxx", jbxx);
				tj.save();
			}
			else{
				tjold.set("xm", pi.get("xm"));
				tjold.set("sfz", pi.get("sfz"));
				tjold.set("tjbh", tjbh );
				tjold.set("tjsj", pi.get("tjsj"));
				tjold.set("jddw", pi.get("tjjgcode"));
				tjold.set("lrdw", pi.get("tjjgcode"));
				tjold.set("lrry", pi.get("tjrycode"));
				tjold.set("zrys", pi.get("tjrycode"));
				tjold.set("wjdc", wjdc);
				tjold.set("ybjc", ybjc);
				tjold.set("rzyd", rzyd);
				tjold.set("nk", nk);
				tjold.set("wk", wk);
				tjold.set("yktj", yktl);
				tjold.set("kqk", kqk);
				tjold.set("xyjc", xyjc);
				tjold.set("nyjc", nyjc);
				tjold.set("xdt", xdt);
				tjold.set("xg", xg);
				tjold.set("bc", bc);
				tjold.set("gmd", gmd);
				tjold.set("czdz", pi.get("jtdzcode"));
				tjold.set("lrdwregioncode", lrdwRegionCode);
				tjold.set("bclx", pi.get("bclx"));
				tjold.set("state", pi.get("state"));
				tjold.set("tjid", tjid);
				tjold.set("jddwname", pi.get("tjjgname"));
				tjold.set("lrdwname", pi.get("tjjgname"));
				tjold.set("lrryname", pi.get("tjryname"));
				tjold.set("zrysname", pi.get("tjryname"));
				tjold.set("jbxx", jbxx);
				tjold.update();
			}
		}
	}

	public void tjxxcx() {
		String whereSql = "";
		if (!xmlData.equals("")) {
			Map m = JsonParser.json2Map(xmlData);
			String searchType = m.get("searchType") == null ? "" : m.get(
					"searchType").toString(); // 查询方式
			String regioncode = m.get("regioncode") == null ? "" : m.get(
					"regioncode").toString();
			String groupid = m.get("groupid") == null ? "" : m.get("groupid")
					.toString(); // 
			String zzjg = m.get("zzjg") == null ? "" : m.get("zzjg").toString(); // 组织机构
			String tjbh = m.get("tjbh") == null ? "" : m.get("tjbh").toString(); // 体检编号
			String xm = m.get("xm") == null ? "" : m.get("xm").toString(); // 姓名
			String sfzh = m.get("sfzh") == null ? "" : m.get("sfzh").toString(); // 身份证号
			String xb = m.get("xb") == null ? "" : m.get("xb").toString(); // 性别
			String nl_s = m.get("nl_s") == null ? "" : m.get("nl_s").toString(); // 
			String nl_e = m.get("nl_e") == null ? "" : m.get("nl_e").toString(); // 
			Date aDate = new Date();
			int thisYear = Integer.parseInt(TimeTool.getYearString());
			String thisMonth = TimeTool.getMonthString();
			String thisDay = TimeTool.getdayString();
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.roll(java.util.Calendar.DAY_OF_YEAR, 1);
			String tomorrow = df.format(calendar.getTime());
			if (!nl_s.equals("") && nl_s.indexOf("-") == -1) {
				nl_s = String.valueOf(thisYear - Integer.parseInt(nl_s)) + "-"
						+ thisMonth + "-" + thisDay;
			}
			if (!nl_e.equals("") && nl_e.indexOf("-") == -1) {
				thisMonth = tomorrow.substring(5, 7);
				thisDay = tomorrow.substring(8);
				nl_e = String.valueOf(thisYear - Integer.parseInt(nl_e) - 1)
						+ "-" + thisMonth + "-" + thisDay;
			}
			String lrrq_s = m.get("lrrq_s") == null ? "" : m.get("lrrq_s")
					.toString(); // 
			String lrrq_e = m.get("lrrq_e") == null ? "" : m.get("lrrq_e")
					.toString(); // 
			String tjrq_s = m.get("tjrq_s") == null ? "" : m.get("tjrq_s")
					.toString(); // 
			String tjrq_e = m.get("tjrq_e") == null ? "" : m.get("tjrq_e")
					.toString(); //
			String hbqk = m.get("hbqk") == null ? "" : m.get("hbqk").toString(); // 患病状况
			String bclx = m.get("bclx") == null ? "" : m.get("bclx").toString(); // 体检保存类型
			StringBuffer sb = new StringBuffer();
			if (StringTool.check(searchType)) {
				if (searchType.equals("1")) { // 机构服务工作量
					sb.append(" and d.tjjgcode in (SELECT id  FROM xt_Group_d CONNECT BY parentid = PRIOR id START WITH id = " + groupid + ")");
				} else if (searchType.equals("2")
						&& StringTool.check(regioncode)) { // 行政区划服务量
					String level = gs.calcFinlvl(regioncode);
					sb.append(" and "
							+ StringTool.getChecksql1("d.jtdzcode", level)
							+ "="
							+ StringTool.getChecksql1("'" + regioncode + "'",
									level));
				}
			}else {
				sb.append(" and d.tjjgcode in (SELECT id  FROM xt_Group_d CONNECT BY parentid = PRIOR id START WITH id = " + groupid + ")");
			}
			if (StringTool.check(xm)) {
				sb.append(" and xm like '" + xm + "%'");
			}
			if (StringTool.check(tjbh)&& tjbh.length()>2) {
				String tjidyear = tjbh.substring(0, 2);
				String tjid2 = tjbh.substring(2, tjbh.length());
				sb.append(" and tjbhyear = '"+ tjidyear + "'  and  tjbh like  '" + tjid2 + "%'"  );
			}
			if (StringTool.check(sfzh)) {
				sb.append(" and sfz like '" + sfzh.toUpperCase() + "%'");
			}
			if (StringTool.check(xb) && !xb.equals("9")) {
				{
					sb.append(" and xb='" + xb + "'");
				}
			}
			if (StringTool.check(nl_s)) {
				sb.append("  and to_char(csrq,'yyyy-mm-dd')<='" + nl_s + "'");
			}
			if (StringTool.check(nl_e)) {
				sb.append("  and to_char(csrq,'yyyy-mm-dd')>='" + nl_e + "'");
			}

			if (StringTool.check(lrrq_s)) {
				sb.append("  and to_char(lrsj,'yyyy-mm-dd')>='" + lrrq_s + "'");
			}
			if (StringTool.check(lrrq_e)) {
				sb.append("  and to_char(lrsj,'yyyy-mm-dd')<='" + lrrq_e + "'");
			}

			if (StringTool.check(tjrq_s)) {
				sb.append("  and to_char(tjsj,'yyyy-mm-dd')>='" + tjrq_s + "'");
			}
			if (StringTool.check(tjrq_e)) {
				sb.append("  and to_char(tjsj,'yyyy-mm-dd')<='" + tjrq_e + "'");
			}
			if (StringTool.check(hbqk) && !hbqk.equals("9")) {
				if(hbqk.equals("2")){//高血压
					sb.append(" and (splitcheckvalue(question2,',','"+ hbqk+"') = '" + 1 + "'  or to_number(xyssy)>=140 or to_number(xyszy)>=90 )");
				}else if(hbqk.equals("3")){//糖尿病
					sb.append(" and (splitcheckvalue(question2,',','"+ hbqk+"') = '" + 1 + "' or to_number(kfxt)>=7  )");
				}
			}
			if (StringTool.check(bclx) && !bclx.equals("9")) {
				if(bclx.equals("1")){//临时保存
					sb.append(" and  bclx = '1'");
				}else if(bclx.equals("2")){//提交
					sb.append(" and  bclx = '2'");
				}
			}
			whereSql += sb;
		}
		String grxxid = this.getPara("grxxid");
		int pageNum = 1;
		if (this.getPara("pageNum") != null) {
			pageNum = this.getParaToInt("pageNum");
		}
		String sql = " from ph_medicalinfo d where 1=1 and d.state='0'  ";
		sql = sql + whereSql;
		sql = sql + " ORDER BY  d.tjbhyear DESC,d.tjbh DESC,d.id ";
		Page p = PhMedicalInfo.dao.paginate(pageNum, 20, "select d.*,d.tjbhyear ||d.tjbh  as tjbhall", sql);
		renderJson(p);
	}

	public void queryTjbh() {
		String tjbh = this.getPara("tjbh");
		String tjbhyear = this.getPara("tjbhyear");
		String path = Db.queryStr("select B.path from PH_MEDICALINFO A ,xt_grouppath B where A.TJJGCODE = B.ID and A.tjbh = '"+ tjbh +"' and A.tjbhyear ='"+ tjbhyear +"'");
//		System.out.println(path);
		PhMedicalInfo phMedicalInfo = PhMedicalInfo.dao
				.findFirst("select d.*  from PH_MEDICALINFO d  where  d.tjbh  = '"
						+ tjbh + "'  and d.tjbhyear = '"+tjbhyear+"'");
		if (phMedicalInfo != null ) {
			map.put("type", "0");
			map.put("data", phMedicalInfo.toJson());
			map.put("path", path);
			renderJson(JsonParser.map2Json(map));
		} else {
			map.put("type", "0");
			renderJson(JsonParser.map2Json(map));
		}
	}

	public void exportExceljy() {
		String whereSql = "";
		if (!xmlData.equals("")) {
			Map m = JsonParser.json2Map(xmlData);
			String searchType = m.get("searchType") == null ? "" : m.get(
					"searchType").toString(); // 查询方式
			String regioncode = m.get("regioncode") == null ? "" : m.get(
					"regioncode").toString();
			String groupid = m.get("groupid") == null ? "" : m.get("groupid")
					.toString(); // 
			String zzjg = m.get("zzjg") == null ? "" : m.get("zzjg").toString(); // 组织机构
			String tjbh = m.get("tjbh") == null ? "" : m.get("tjbh").toString(); // 体检编号
			String xm = m.get("xm") == null ? "" : m.get("xm").toString(); // 姓名
			String sfzh = m.get("sfzh") == null ? "" : m.get("sfzh").toString(); // 身份证号
			String xb = m.get("xb") == null ? "" : m.get("xb").toString(); // 性别
			String nl_s = m.get("nl_s") == null ? "" : m.get("nl_s").toString(); // 
			String nl_e = m.get("nl_e") == null ? "" : m.get("nl_e").toString(); // 
			Date aDate = new Date();
			int thisYear = Integer.parseInt(TimeTool.getYearString());
			String thisMonth = TimeTool.getMonthString();
			String thisDay = TimeTool.getdayString();
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.roll(java.util.Calendar.DAY_OF_YEAR, 1);
			String tomorrow = df.format(calendar.getTime());
			if (!nl_s.equals("") && nl_s.indexOf("-") == -1) {
				nl_s = String.valueOf(thisYear - Integer.parseInt(nl_s)) + "-"
						+ thisMonth + "-" + thisDay;
			}
			if (!nl_e.equals("") && nl_e.indexOf("-") == -1) {
				thisMonth = tomorrow.substring(5, 7);
				thisDay = tomorrow.substring(8);
				nl_e = String.valueOf(thisYear - Integer.parseInt(nl_e) - 1)
						+ "-" + thisMonth + "-" + thisDay;
			}
			String lrrq_s = m.get("lrrq_s") == null ? "" : m.get("lrrq_s")
					.toString(); // 
			String lrrq_e = m.get("lrrq_e") == null ? "" : m.get("lrrq_e")
					.toString(); // 
			String tjrq_s = m.get("tjrq_s") == null ? "" : m.get("tjrq_s")
					.toString(); // 
			String tjrq_e = m.get("tjrq_e") == null ? "" : m.get("tjrq_e")
					.toString(); //
			String hbqk = m.get("hbqk") == null ? "" : m.get("hbqk").toString(); // 患病状况
			String bclx = m.get("bclx") == null ? "" : m.get("bclx").toString(); // 体检保存类型
			StringBuffer sb = new StringBuffer();
			if (StringTool.check(searchType)) {
				if (searchType.equals("1")) { // 机构服务工作量
					sb.append(" and d.tjjgcode in (SELECT id  FROM xt_Group_d CONNECT BY parentid = PRIOR id START WITH id = " + groupid + ")");
				} else if (searchType.equals("2")
						&& StringTool.check(regioncode)) { // 行政区划服务量
					String level = gs.calcFinlvl(regioncode);
					sb.append(" and "
							+ StringTool.getChecksql1("d.jtdzcode", level)
							+ "="
							+ StringTool.getChecksql1("'" + regioncode + "'",
									level));
				}
			}else {
				sb.append(" and d.tjjgcode in (SELECT id  FROM xt_Group_d CONNECT BY parentid = PRIOR id START WITH id = " + groupid + ")");
			}
			if (StringTool.check(xm)) {
				sb.append(" and xm like '" + xm + "%'");
			}
			if (StringTool.check(tjbh)) {
				sb.append(" and tjbhall like '" + tjbh + "%'");
			}
			if (StringTool.check(sfzh)) {
				sb.append(" and sfz like'" + sfzh.toUpperCase() + "%'");
			}
			if (StringTool.check(xb) && !xb.equals("9")) {
				{
					sb.append(" and xb='" + xb + "'");
				}
			}
			if (StringTool.check(nl_s)) {
				sb.append("  and to_char(to_date(csrq,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + nl_s + "'");
			}
			if (StringTool.check(nl_e)) {
				sb.append("  and to_char(to_date(csrq,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + nl_e + "'");
			}

			if (StringTool.check(lrrq_s)) {
				sb.append("  and to_char(to_date(lrsj,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + lrrq_s + "'");
			}
			if (StringTool.check(lrrq_e)) {
				sb.append("  and to_char(to_date(lrsj,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + lrrq_e + "'");
			}

			if (StringTool.check(tjrq_s)) {
				sb.append("  and to_char(to_date(tjsj,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + tjrq_s + "'");
			}
			if (StringTool.check(tjrq_e)) {
				sb.append("  and to_char(to_date(tjsj,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + tjrq_e + "'");
			}
			if (StringTool.check(hbqk) && !hbqk.equals("9")) {
				if(hbqk.equals("2")){//高血压
					sb.append(" and (splitcheckvalue(question2,',','"+ hbqk+"') = '" + 1 + "'  or to_number(ssy)>=140 or to_number(szy)>=90 )");
				}else if(hbqk.equals("3")){//糖尿病
					sb.append(" and (splitcheckvalue(question2,',','"+ hbqk+"') = '" + 1 + "'  or to_number(kfxtz)>=7  )");
				}
			}
			if (StringTool.check(bclx) && !bclx.equals("9")) {
				if(bclx.equals("1")){//临时保存
					sb.append(" and  bclx = '1'");
				}else if(bclx.equals("2")){//提交
					sb.append(" and  bclx = '2'");
				}
			}
			whereSql += sb;
		}
		String sql = "select tjbhall, tjsj, tjjg, xm, sfz, xb, csrq, nl, hyzk, whcd, jtzzxzq, jdmc, jtzzxq, "
				+ "lxfsgh, lxfssj, jsxm, jslxfs, zshygxy, zshytnb, ssy, szy, fyqh, sg, tz, yw, tzzs, xdt, xbxg, fbbc, gmd, kfxtz, gysz, zdgc, "
				+ "gmdzdb, dmdzdb, xqns from view_tjxxjy d where 1=1  and d.state='0' ";
		sql = sql + whereSql;
		sql = sql + "ORDER BY  d.tjbhall DESC";
		BigDecimal listsize = Db.queryBigDecimal("select count(*) from ("+sql+")");
		if( listsize.compareTo(BigDecimal.valueOf(15000)) == 1 ){
			map.put("type", "0");
			map.put("onloadstyle", "0");
			map.put("context", "您此次导出数据大于15000，建议您分批导出。（可通过时间段查询分批导出）");
			renderJson(JsonParser.map2Json(map));
		}else{
			// 导出
			List<TjxxjyView> list = TjxxjyView.dao.find(sql);
			String downpath = service.exporttjxxjy(getResponse(), getRequest(), list);
			map.put("type", "0");
			map.put("onloadstyle", "1");
			map.put("path", downpath);
		}
		renderJson(JsonParser.map2Json(map));
		
	}

	public void exportExcelwz() {
		
		Map user = (Map) this.getSession().getAttribute("uerroleinfo");
		if (user == null) {
			map.put("type", "1");
			map.put("content", "登录用户信息失效，请重新登陆！");
			renderJson(JsonParser.map2Json(map));
			return;
		}
		Map groupinfo = JsonParser.json2Map(user.get("ENTERPRISE").toString());
		// String groupinfo = (String) user.get("ENTERPRISE");
		if (groupinfo == null || groupinfo.equals("")) {
			map.put("type", "1");
			map.put("content", "登录用户信息失效，请重新登陆！");
			renderJson(JsonParser.map2Json(map));
			return;
		}
		String operateRegioncode = groupinfo.get("f_regioncode").toString();
		
		String whereSql = "";
		if (!xmlData.equals("")) {
			Map m = JsonParser.json2Map(xmlData);
			String searchType = m.get("searchType") == null ? "" : m.get(
					"searchType").toString(); // 查询方式
			String regioncode = m.get("regioncode") == null ? "" : m.get(
					"regioncode").toString();
			String groupid = m.get("groupid") == null ? "" : m.get("groupid")
					.toString(); // 
			String zzjg = m.get("zzjg") == null ? "" : m.get("zzjg").toString(); // 组织机构
			String tjbh = m.get("tjbh") == null ? "" : m.get("tjbh").toString(); // 体检编号
			String xm = m.get("xm") == null ? "" : m.get("xm").toString(); // 姓名
			String sfzh = m.get("sfzh") == null ? "" : m.get("sfzh").toString(); // 身份证号
			String xb = m.get("xb") == null ? "" : m.get("xb").toString(); // 性别
			String nl_s = m.get("nl_s") == null ? "" : m.get("nl_s").toString(); // 
			String nl_e = m.get("nl_e") == null ? "" : m.get("nl_e").toString(); // 
			Date aDate = new Date();
			int thisYear = Integer.parseInt(TimeTool.getYearString());
			String thisMonth = TimeTool.getMonthString();
			String thisDay = TimeTool.getdayString();
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.roll(java.util.Calendar.DAY_OF_YEAR, 1);
			String tomorrow = df.format(calendar.getTime());
			if (!nl_s.equals("") && nl_s.indexOf("-") == -1) {
				nl_s = String.valueOf(thisYear - Integer.parseInt(nl_s)) + "-"
						+ thisMonth + "-" + thisDay;
			}
			if (!nl_e.equals("") && nl_e.indexOf("-") == -1) {
				thisMonth = tomorrow.substring(5, 7);
				thisDay = tomorrow.substring(8);
				nl_e = String.valueOf(thisYear - Integer.parseInt(nl_e) - 1)
						+ "-" + thisMonth + "-" + thisDay;
			}
			String lrrq_s = m.get("lrrq_s") == null ? "" : m.get("lrrq_s")
					.toString(); // 
			String lrrq_e = m.get("lrrq_e") == null ? "" : m.get("lrrq_e")
					.toString(); // 
			String tjrq_s = m.get("tjrq_s") == null ? "" : m.get("tjrq_s")
					.toString(); // 
			String tjrq_e = m.get("tjrq_e") == null ? "" : m.get("tjrq_e")
					.toString(); //
			String hbqk = m.get("hbqk") == null ? "" : m.get("hbqk").toString(); // 患病状况
			String bclx = m.get("bclx") == null ? "" : m.get("bclx").toString(); // 体检保存类型
			StringBuffer sb = new StringBuffer();
			if (StringTool.check(searchType)) {
				if (searchType.equals("1")) { // 机构服务工作量
					sb.append(" and d.tjjgcode in (SELECT id  FROM xt_Group_d CONNECT BY parentid = PRIOR id START WITH id = " + groupid + ")");
				} else if (searchType.equals("2")
						&& StringTool.check(regioncode)) { // 行政区划服务量
					String level = gs.calcFinlvl(regioncode);
					sb.append(" and "
							+ StringTool.getChecksql1("d.jtdzcode", level)
							+ "="
							+ StringTool.getChecksql1("'" + regioncode + "'",
									level));
				}
			}else {
				sb.append(" and d.tjjgcode in (SELECT id  FROM xt_Group_d CONNECT BY parentid = PRIOR id START WITH id = " + groupid + ")");
			}
			if (StringTool.check(xm)) {
				sb.append(" and xm like '" + xm + "%'");
			}
			if (StringTool.check(tjbh)) {
				sb.append(" and tjbhall like '" + tjbh + "%'");
			}
			if (StringTool.check(sfzh)) {
				sb.append(" and sfz like'" + sfzh.toUpperCase() + "%'");
			}
			if (StringTool.check(xb) && !xb.equals("9")) {
				{
					sb.append(" and xb='" + xb + "'");
				}
			}
			if (StringTool.check(nl_s)) {
				sb.append("  and to_char(to_date(csrq,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + nl_s + "'");
			}
			if (StringTool.check(nl_e)) {
				sb.append("  and to_char(to_date(csrq,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + nl_e + "'");
			}

			if (StringTool.check(lrrq_s)) {
				sb.append("  and to_char(to_date(lrsj,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + lrrq_s + "'");
			}
			if (StringTool.check(lrrq_e)) {
				sb.append("  and to_char(to_date(lrsj,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + lrrq_e + "'");
			}

			if (StringTool.check(tjrq_s)) {
				sb.append("  and to_char(to_date(tjsj,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + tjrq_s + "'");
			}
			if (StringTool.check(tjrq_e)) {
				sb.append("  and to_char(to_date(tjsj,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + tjrq_e + "'");
			}
			if (StringTool.check(hbqk) && !hbqk.equals("9")) {
				if(hbqk.equals("2")){//高血压
					sb.append(" and (splitcheckvalue(question2,',','"+ hbqk+"') = '" + 1 + "'  or to_number(ssy)>=140 or to_number(szy)>=90 )");
				}else if(hbqk.equals("3")){//糖尿病
					sb.append(" and (splitcheckvalue(question2,',','"+ hbqk+"') = '" + 1 + "'  or to_number(kfxtz)>=7  )");
				}
			}
			if (StringTool.check(bclx) && !bclx.equals("9")) {
				if(bclx.equals("1")){//临时保存
					sb.append(" and  bclx = '1'");
				}else if(bclx.equals("2")){//提交
					sb.append(" and  bclx = '2'");
				}
			}
			whereSql += sb;
		}
		String sql = "select tjbhall, xzqmc, lrjg, tjsj, lrsj, xm, sfz, xb, csrq, nl, hyzk, whcd, jzbs_gxy, jzbs_tnb, jzbs_zf, " +
				"jzbs_gxb, jzbs_gzxz, jzbs_az, jzbs_xc, jzbs_lncd, jzbsqt, jzbs_qtjt, gms, gmsyw, gmssw, gmsqt, jtzzxzq, jdmc, jtzzxq, " +
				"lxfsgh, lxfssj, jsxm, jslxfs, zz_wzz, ks, kt, szt, bzg_y, dpt, lbt, bs, kbqdx, yjg, yjt_h_y, tt, qt, qtjt1, hyjb_w, " +
				"hyjbgxy, gxyqzsj, gxyfy, gxyywlf, gxylfsj, hyjbtnb, tnbqzsj, tnbfy, tnbywlf, tnblfsj, hyjbnzf, nqx1, nqxfzcs, nqxsfsj_n, " +
				"nqxsfsj_y, nqxzhfzsj_n, nqxzhfzsj_y, ncx, ncxfzcs, ncxsfsj_n, ncxsfsj_y, ncxzhfzsj_n, ncxzhfzsj_y, dzxnqxtia, tiafzcs, " +
				"tiasfsj_n, tiasfsj_y, tiazhfzsj_n, tiazhfzsj_y, hyjbxjgs, xjgsfzcs, xjgssfsj_n, xjgssfsj_y, xjgszhfzsj_n, xjgszhfzsj_y, " +
				"hyjbgxb, hyjbgzxz, hyjblmz, hyjbxc, hyjbbnz, hyjbqgy, hyjbsjs, hyjbdjs, hyjbqlxjb, hyjbggjjb, hyjbzfg, hyjbtf, hyjbqt, " +
				"qtjt2, zycs, jyzkbqk, jkmyzk, shqjzlqk, jwldzlqk, zgshqj, csgts, csgfs, cscts, cscfl, cdwy, gqddl, gqddlpl_t, gqddlsj_xs, " +
				"gqddlsj_fz, zdqddl, zdqddlpl_t, zdqddlsj_xs, zdqddlsj_fz, xy, xyl_z, xyns, hj, hjpl, hjl, ssqk, dcsj, ssy, szy, fyqh, sg, " +
				"tz, yw, tzzs, wpy, wpe, wps, fsqk, fzhfsqk, xq, ssfnh, jqdswp, zqzszx, bydz, xl1, xl2, zy, zyqt, sfwtzx, hxy, hxyqt, ly, " +
				"lyqt, fbyt, fbytqt, fbbk, fbbkqt, gd, gdqt, pd, pdqt, fbydxzy, pf, pfqt, qblbj, qblbjqt, xzsz, zbdmbdwcj, zbdmcjscdc, zbdmcjzcr, " +
				"zbdmcjycr, lyslz, lysly, jzslz, jzsly, gm_zc, gm_hr, gm_cx, gm_yrzsw, gm_bnzhby, gm_qt, gmqt, tl, kc, clsfzc, qc1, qcs1, qc2, qcs2, jy, jys, yb, xdtsfzc, xdtzb, xdtzxgh, " +
				"xdtzxbq, xdtstdyc, xdtcdzz, xdtqrsbyc, xdtqt, xdtjcqtjt, xbxg, xbxgzqgy, xbxgfjh, xbxgxyzd, xbxgfqz, xbxgfy, xbxgfyx, xbxgfwlzc, " +
				"xbxgqt, xbxgqtjt, fbbc, fbbczfg, fbbcgnz, fbbcxxcg, fbbcgxgl, fbbcdjs, fbbcdny, fbbcdnxr, fbbcdnqc, fbbcsnz, fbbcsjs1, fbbcsjs2, " +
				"fbbcqt, fbbcycqtjt, gmd, gmdclbw, bxbjs, bxbjszsz, hxbjs, hxbjszs, xhdb, xhdbzsz, xxbjs, xxbjszsz, hxbyj, hxbyjzs, nptt, nbxb, " +
				"ndbz, nqx2, nyxsy, ndy, ndhs, ntt, nphz, nbz, kfxtz, kfxtzzsz, gysz, gyszzsz, zdgc, zdgczsz, gmdzdgc, gmdzdgczsz, dmdzdgc, dmdzdgczsz, " +
				"xqbaszam, xqbaszamzsz, xqtdaszam, xqtdaszamzsz, zdhs, zdhszsz, zdb, zdbzsz, bdb, bdbzsz, qdb, qdbzsz, xqjz, xqjzzsz, xnsd, xnsdzsz, xqns, " +
				"xqnszsz, sysjcjg  from view_tjxxwz d where 1=1 and d.state='0' ";
		sql = sql + whereSql;
		
		String level = gs.calcFinlvl(operateRegioncode);
		//如果登录用户为武汉市级别，导出语句修改为此
		if(level!=null&&level.equals("2")){
			sql = "select tjbhall, xzqmc, lrjg, tjsj, lrsj, xm, sfz, xb, csrq, nl, hyzk, whcd, jzbs_gxy, jzbs_tnb, jzbs_zf, " +
				"jzbs_gxb, jzbs_gzxz, jzbs_az, jzbs_xc, jzbs_lncd, jzbsqt, jzbs_qtjt, gms, gmsyw, gmssw, gmsqt, jtzzxzq, jdmc, jtzzxq, " +
				"lxfsgh, lxfssj, jsxm, jslxfs, zz_wzz, ks, kt, szt, bzg_y, dpt, lbt, bs, kbqdx, yjg, yjt_h_y, tt, qt, qtjt1, hyjb_w, " +
				"hyjbgxy, gxyqzsj, gxyfy, gxyywlf, gxylfsj, hyjbtnb, tnbqzsj, tnbfy, tnbywlf, tnblfsj, hyjbnzf, nqx1, nqxfzcs, nqxsfsj_n, " +
				"nqxsfsj_y, nqxzhfzsj_n, nqxzhfzsj_y, ncx, ncxfzcs, ncxsfsj_n, ncxsfsj_y, ncxzhfzsj_n, ncxzhfzsj_y, dzxnqxtia, tiafzcs, " +
				"tiasfsj_n, tiasfsj_y, tiazhfzsj_n, tiazhfzsj_y, hyjbxjgs, xjgsfzcs, xjgssfsj_n, xjgssfsj_y, xjgszhfzsj_n, xjgszhfzsj_y, " +
				"hyjbgxb, hyjbgzxz, hyjblmz, hyjbxc, hyjbbnz, hyjbqgy, hyjbsjs, hyjbdjs, hyjbqlxjb, hyjbggjjb, hyjbzfg, hyjbtf, hyjbqt, " +
				"qtjt2, zycs, jyzkbqk, jkmyzk, shqjzlqk, jwldzlqk, zgshqj, csgts, csgfs, cscts, cscfl, cdwy, gqddl, gqddlpl_t, gqddlsj_xs, " +
				"gqddlsj_fz, zdqddl, zdqddlpl_t, zdqddlsj_xs, zdqddlsj_fz, xy, xyl_z, xyns, hj, hjpl, hjl, ssqk, dcsj, ssy, szy, fyqh, sg, " +
				"tz, yw, tzzs, wpy, wpe, wps, fsqk, fzhfsqk, xq, ssfnh, jqdswp, zqzszx, bydz, xl1, xl2, zy, zyqt, sfwtzx, hxy, hxyqt, ly, " +
				"lyqt, fbyt, fbytqt, fbbk, fbbkqt, gd, gdqt, pd, pdqt, fbydxzy, pf, pfqt, qblbj, qblbjqt, xzsz, zbdmbdwcj, zbdmcjscdc, zbdmcjzcr, " +
				"zbdmcjycr, lyslz, lysly, jzslz, jzsly, gm_zc, gm_hr, gm_cx, gm_yrzsw, gm_bnzhby, gm_qt, gmqt, tl, kc, clsfzc, qc1, qcs1, qc2, qcs2, jy, jys, yb, xdtsfzc, xdtzb, xdtzxgh, " +
				"xdtzxbq, xdtstdyc, xdtcdzz, xdtqrsbyc, xdtqt, xdtjcqtjt, xbxg, xbxgzqgy, xbxgfjh, xbxgxyzd, xbxgfqz, xbxgfy, xbxgfyx, xbxgfwlzc, " +
				"xbxgqt, xbxgqtjt, fbbc, fbbczfg, fbbcgnz, fbbcxxcg, fbbcgxgl, fbbcdjs, fbbcdny, fbbcdnxr, fbbcdnqc, fbbcsnz, fbbcsjs1, fbbcsjs2, " +
				"fbbcqt, fbbcycqtjt, gmd, gmdclbw, bxbjs, bxbjszsz, hxbjs, hxbjszs, xhdb, xhdbzsz, xxbjs, xxbjszsz, hxbyj, hxbyjzs, nptt, nbxb, " +
				"ndbz, nqx2, nyxsy, ndy, ndhs, ntt, nphz, nbz, kfxtz, kfxtzzsz, gysz, gyszzsz, zdgc, zdgczsz, gmdzdgc, gmdzdgczsz, dmdzdgc, dmdzdgczsz, " +
				"xqbaszam, xqbaszamzsz, xqtdaszam, xqtdaszamzsz, zdhs, zdhszsz, zdb, zdbzsz, bdb, bdbzsz, qdb, qdbzsz, xqjz, xqjzzsz, xnsd, xnsdzsz, xqns, " +
				"xqnszsz, sysjcjg  from view_tjxxwz_wh d where 1=1 and d.state='0' ";
			sql = sql + whereSql;
			
		}
		
		BigDecimal listsize = Db.queryBigDecimal("select count(*) from ("+sql+")");
		if( listsize.compareTo(BigDecimal.valueOf(15000)) == 1 ){
			map.put("type", "0");
			map.put("onloadstyle", "0");
			map.put("context", "您此次导出数据大于15000，建议您分批导出。（可通过时间段查询分批导出）");
			renderJson(JsonParser.map2Json(map));
		}else{
			// 导出
			List<TjxxwzView> list = TjxxwzView.dao.find(sql);
			String downpath = service.exporttjxxwz(getResponse(), getRequest(), list);
			map.put("type", "0");
			map.put("onloadstyle", "1");
			map.put("path", downpath);
		}
		renderJson(JsonParser.map2Json(map));
	}
	
	
	public void exportXml() {
		
		Map user = (Map) this.getSession().getAttribute("uerroleinfo");
		if (user == null) {
			map.put("type", "1");
			map.put("content", "登录用户信息失效，请重新登陆！");
			renderJson(JsonParser.map2Json(map));
			return;
		}
		Map groupinfo = JsonParser.json2Map(user.get("ENTERPRISE").toString());
		// String groupinfo = (String) user.get("ENTERPRISE");
		if (groupinfo == null || groupinfo.equals("")) {
			map.put("type", "1");
			map.put("content", "登录用户信息失效，请重新登陆！");
			renderJson(JsonParser.map2Json(map));
			return;
		}
		String operateRegioncode = groupinfo.get("f_regioncode").toString();
		
		String level2 = gs.calcFinlvl(operateRegioncode);
		//如果登录用户为武汉市级别，允许导出xml
		if(level2!=null&&level2.equals("2")){
			String whereSql = "";
			if (!xmlData.equals("")) {
				Map m = JsonParser.json2Map(xmlData);
				String searchType = m.get("searchType") == null ? "" : m.get(
						"searchType").toString(); // 查询方式
				String regioncode = m.get("regioncode") == null ? "" : m.get(
						"regioncode").toString();
				String groupid = m.get("groupid") == null ? "" : m.get("groupid")
						.toString(); // 
				String zzjg = m.get("zzjg") == null ? "" : m.get("zzjg").toString(); // 组织机构
				String tjbh = m.get("tjbh") == null ? "" : m.get("tjbh").toString(); // 体检编号
				String xm = m.get("xm") == null ? "" : m.get("xm").toString(); // 姓名
				String sfzh = m.get("sfzh") == null ? "" : m.get("sfzh").toString(); // 身份证号
				String xb = m.get("xb") == null ? "" : m.get("xb").toString(); // 性别
				String nl_s = m.get("nl_s") == null ? "" : m.get("nl_s").toString(); // 
				String nl_e = m.get("nl_e") == null ? "" : m.get("nl_e").toString(); // 
				Date aDate = new Date();
				int thisYear = Integer.parseInt(TimeTool.getYearString());
				String thisMonth = TimeTool.getMonthString();
				String thisDay = TimeTool.getdayString();
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
						"yyyy-MM-dd");
				java.util.Calendar calendar = java.util.Calendar.getInstance();
				calendar.roll(java.util.Calendar.DAY_OF_YEAR, 1);
				String tomorrow = df.format(calendar.getTime());
				if (!nl_s.equals("") && nl_s.indexOf("-") == -1) {
					nl_s = String.valueOf(thisYear - Integer.parseInt(nl_s)) + "-"
							+ thisMonth + "-" + thisDay;
				}
				if (!nl_e.equals("") && nl_e.indexOf("-") == -1) {
					thisMonth = tomorrow.substring(5, 7);
					thisDay = tomorrow.substring(8);
					nl_e = String.valueOf(thisYear - Integer.parseInt(nl_e) - 1)
							+ "-" + thisMonth + "-" + thisDay;
				}
				String lrrq_s = m.get("lrrq_s") == null ? "" : m.get("lrrq_s")
						.toString(); // 
				String lrrq_e = m.get("lrrq_e") == null ? "" : m.get("lrrq_e")
						.toString(); // 
				String tjrq_s = m.get("tjrq_s") == null ? "" : m.get("tjrq_s")
						.toString(); // 
				String tjrq_e = m.get("tjrq_e") == null ? "" : m.get("tjrq_e")
						.toString(); //
				String hbqk = m.get("hbqk") == null ? "" : m.get("hbqk").toString(); // 患病状况
				String bclx = m.get("bclx") == null ? "" : m.get("bclx").toString(); // 体检保存类型
				StringBuffer sb = new StringBuffer();
				if (StringTool.check(searchType)) {
					if (searchType.equals("1")) { // 机构服务工作量
						sb.append(" and d.tjjgcode in (SELECT id  FROM xt_Group_d CONNECT BY parentid = PRIOR id START WITH id = " + groupid + ")");
					} else if (searchType.equals("2")
							&& StringTool.check(regioncode)) { // 行政区划服务量
						String level = gs.calcFinlvl(regioncode);
						sb.append(" and "
								+ StringTool.getChecksql1("d.jtdzcode", level)
								+ "="
								+ StringTool.getChecksql1("'" + regioncode + "'",
										level));
					}
				}else {
					sb.append(" and d.tjjgcode in (SELECT id  FROM xt_Group_d CONNECT BY parentid = PRIOR id START WITH id = " + groupid + ")");
				}
				if (StringTool.check(xm)) {
					sb.append(" and xm like '" + xm + "%'");
				}
				if (StringTool.check(tjbh)) {
					sb.append(" and tjbhall like '" + tjbh + "%'");
				}
				if (StringTool.check(sfzh)) {
					sb.append(" and sfz like'" + sfzh.toUpperCase() + "%'");
				}
				if (StringTool.check(xb) && !xb.equals("9")) {
					{
						sb.append(" and xb='" + xb + "'");
					}
				}
				if (StringTool.check(nl_s)) {
					sb.append("  and to_char(to_date(csrq,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + nl_s + "'");
				}
				if (StringTool.check(nl_e)) {
					sb.append("  and to_char(to_date(csrq,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + nl_e + "'");
				}

				if (StringTool.check(lrrq_s)) {
					sb.append("  and to_char(to_date(lrsj,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + lrrq_s + "'");
				}
				if (StringTool.check(lrrq_e)) {
					sb.append("  and to_char(to_date(lrsj,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + lrrq_e + "'");
				}

				if (StringTool.check(tjrq_s)) {
					sb.append("  and to_char(to_date(tjsj,'yyyy-mm-dd'),'yyyy-mm-dd')>='" + tjrq_s + "'");
				}
				if (StringTool.check(tjrq_e)) {
					sb.append("  and to_char(to_date(tjsj,'yyyy-mm-dd'),'yyyy-mm-dd')<='" + tjrq_e + "'");
				}
				if (StringTool.check(hbqk) && !hbqk.equals("9")) {
					if(hbqk.equals("2")){//高血压
						sb.append(" and (splitcheckvalue(question2,',','"+ hbqk+"') = '" + 1 + "'  or to_number(ssy)>=140 or to_number(szy)>=90 )");
					}else if(hbqk.equals("3")){//糖尿病
						sb.append(" and (splitcheckvalue(question2,',','"+ hbqk+"') = '" + 1 + "'  or to_number(kfxtz)>=7  )");
					}
				}
				if (StringTool.check(bclx) && !bclx.equals("9")) {
					if(bclx.equals("1")){//临时保存
						sb.append(" and  bclx = '1'");
					}else if(bclx.equals("2")){//提交
						sb.append(" and  bclx = '2'");
					}
				}
				whereSql += sb;
			}
			String sql = "select count(d.id)  from ph_medicalinfo d,health_platform e where d.tjjgcode=e.cdc_orgcode " +
					"and e.health_orgcode is not null and d.state='0' ";
			sql = sql + whereSql;

			// 导出
			//List<PhMedicalInfo> list = PhMedicalInfo.dao.find(sql);
			//String downpath = service.exportxml(getResponse(), getRequest(), list);
			map.put("type", "0");
			map.put("onloadstyle", "0");
			map.put("path", "禁用！");
			
		}
		else{
			map.put("type", "0");
			map.put("onloadstyle", "0");
			map.put("context", "您没有导出xml的权限！");
			
		}
		renderJson(JsonParser.map2Json(map));
	}
	
	
	public void printTjall() {
		String tjxxid = this.getPara("tjxxid");
		PhMedicalInfo pi = PhMedicalInfo.dao.findById(tjxxid);
		setAttr("phMedicalInfo", pi);
		render("../pages/tjpzgl/tjall_print.jsp");
	}
	public void printTjxx() {
		String tjxxid = this.getPara("tjxxid");
		PhMedicalInfo pi = PhMedicalInfo.dao.findById(tjxxid);
		setAttr("phMedicalInfo", pi);
		render("../pages/tjpzgl/tj_print.jsp");
	}
	public void printJgjy() {
		String tjxxid = this.getPara("tjxxid");
		PhMedicalInfo pi = PhMedicalInfo.dao.findById(tjxxid);
		setAttr("phMedicalInfo", pi);
		render("../pages/tjpzgl/tjjgjy_print.jsp");
	}
	public void printTjbg() {
		String tjxxid = this.getPara("tjxxid");
		PhMedicalInfo pi = PhMedicalInfo.dao.findById(tjxxid);
		setAttr("phMedicalInfo", pi);
		render("../pages/tjpzgl/tjbg_print.jsp");
	}
	public void printTjbgsimple() {
		String tjxxid = this.getPara("tjxxid");
		PhMedicalInfo pi = PhMedicalInfo.dao.findById(tjxxid);
		setAttr("phMedicalInfo", pi);
		render("../pages/tjpzgl/tjbgsimple_print.jsp");
	}
	public void downloadmb() {
		renderJson("{'path':'../../Templates.rar'}");
	}
}*/