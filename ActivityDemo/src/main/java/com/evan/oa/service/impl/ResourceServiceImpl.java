package com.evan.oa.service.impl;

import com.evan.oa.util.BeanUtils;
import com.evan.oa.dao.IJdbcDao;
import com.evan.oa.entity.GroupAndResource;
import com.evan.oa.entity.Resource;
import com.evan.oa.pagination.Page;
import com.evan.oa.service.IBaseService;
import com.evan.oa.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceServiceImpl implements IResourceService {

	@Autowired
	private IBaseService<Resource> baseService;
	
	@Autowired
	protected IJdbcDao jdbcDao;
	
	@Override
	public Resource getPermissions(Integer id) throws Exception {
		Resource res = this.baseService.getUnique("Resource", new String[]{"id", "available"}, new String[]{id.toString(), "1"});
		return res;
	}

	@Override
	public List<Resource> getMenus(List<GroupAndResource> gr) throws Exception {
		List<Resource> menus = new ArrayList<Resource>();
		for(GroupAndResource gar : gr){
			Resource resource= getPermissions(gar.getResourceId());
			if(!BeanUtils.isBlank(resource)){
				if(resource.isRootNode()) {
	                continue;
	            }
	            if(!"menu".equals(resource.getType())) {
	                continue;
	            }
	            menus.add(resource);
			}
		}
		return menus;
	}

	@Override
	public List<Resource> getAllResource() throws Exception {
		return this.baseService.getAllList("Resource");
	}

	@Override
	public List<Resource> getResourceListPage() throws Exception {
		return this.baseService.findByPage("Resource", new String[]{"available"}, new String[]{"1"});
	}

	@Override
	public List<Resource> getResourceByType() throws Exception {
		return this.baseService.findByWhere("Resource", new String[]{"type"}, new String[]{"menu"});
	}

	@Override
	public void doAdd(Resource entity) throws Exception {
		this.baseService.add(entity);
		
	}

	@Override
	public void doUpdate(Resource entity) throws Exception {
		this.baseService.update(entity);
		
	}

	@Override
	public void doDelete(Resource entity) throws Exception {
		this.baseService.delete(entity);
		
	}

	@Override
	public List<Resource> getResourceList(Page<Resource> p) throws Exception {
		return this.baseService.getListPage("Resource", new String[]{"available"}, new String[]{"1"}, p);
	}

	@Override
	public void doDelete(String id) throws Exception {
		String sql = "delete from T_RESOURCE where id=:id ";
		Map<String, Object> paramMap = new HashMap<String, Object>();  
	    paramMap.put("id", id);  
		this.jdbcDao.delete(sql, paramMap);
		
	}

}
