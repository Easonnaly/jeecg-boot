package org.jeecg.modules.test.test.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.test.test.entity.ContractMain;
import org.jeecg.modules.test.test.service.IContractMainService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

 /**
 * @Description: 合同管理主信息
 * @Author: jeecg-boot
 * @Date:   2019-11-05
 * @Version: V1.0
 */
@RestController
@RequestMapping("/test/contractMain")
@Slf4j
public class ContractMainController extends JeecgController<ContractMain, IContractMainService> {
	@Autowired
	private IContractMainService contractMainService;
	
	/**
	 * 分页列表查询
	 *
	 * @param contractMain
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ContractMain contractMain,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ContractMain> queryWrapper = QueryGenerator.initQueryWrapper(contractMain, req.getParameterMap());
		Page<ContractMain> page = new Page<ContractMain>(pageNo, pageSize);
		IPage<ContractMain> pageList = contractMainService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param contractMain
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ContractMain contractMain) {
		contractMainService.save(contractMain);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param contractMain
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ContractMain contractMain) {
		contractMainService.updateById(contractMain);
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
		contractMainService.removeById(id);
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
		this.contractMainService.removeByIds(Arrays.asList(ids.split(",")));
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
		ContractMain contractMain = contractMainService.getById(id);
		if(contractMain==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(contractMain);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param contractMain
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ContractMain contractMain) {
        return super.exportXls(request, contractMain, ContractMain.class, "合同管理主信息");

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
        return super.importExcel(request, response, ContractMain.class);
    }

}
