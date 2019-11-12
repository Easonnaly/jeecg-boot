package org.jeecg.modules.test.business.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.test.business.entity.SaasOrder;
import org.jeecg.modules.test.business.service.ISaasOrderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2019-11-04
 * @Version: V1.0
 */
@RestController
@RequestMapping("/business/saasOrder")
@Slf4j
public class SaasOrderController extends JeecgController<SaasOrder, ISaasOrderService> {
	@Autowired
	private ISaasOrderService saasOrderService;
	
	/**
	 * 分页列表查询
	 *
	 * @param saasOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SaasOrder saasOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SaasOrder> queryWrapper = QueryGenerator.initQueryWrapper(saasOrder, req.getParameterMap());
		Page<SaasOrder> page = new Page<SaasOrder>(pageNo, pageSize);
		IPage<SaasOrder> pageList = saasOrderService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param saasOrder
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SaasOrder saasOrder) {
		saasOrderService.save(saasOrder);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param saasOrder
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SaasOrder saasOrder) {
		saasOrderService.updateById(saasOrder);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		saasOrderService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.saasOrderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SaasOrder saasOrder = saasOrderService.getById(id);
		if(saasOrder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(saasOrder);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param saasOrder
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SaasOrder saasOrder) {
        return super.exportXls(request, saasOrder, SaasOrder.class, "订单表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, SaasOrder.class);
    }

}
