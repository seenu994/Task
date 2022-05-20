
package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.ClientRepository;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.FeatureRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.apiresponses.IssueTrackerResponse;
import com.xyram.ticketingTool.entity.Client;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Feature;
import com.xyram.ticketingTool.entity.ProjectFeature;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.enumType.ProjectStatus;
import com.xyram.ticketingTool.exception.ResourceNotFoundException;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.ProjectFeatureService;
import com.xyram.ticketingTool.service.ProjectService;
import com.xyram.ticketingTool.util.ResponseMessages;

/**
 * 
 * @author sahana.neelappa
 *
 */

@Service

@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	CurrentUser userDetail;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	FeatureRepository featureRepository;

	@Autowired
	ProjectFeatureService projectFeatureService;

	@Autowired
	ClientRepository clientRepository;

	@Override
	public ApiResponse addproject(Projects project) {
		ApiResponse response = new ApiResponse(false);
		response = validateProject(project);
		if (response.getMessage() != null && response.getMessage() != "") {
			return response;
		}
		// if (response.isSuccess()) {
		// projectRepository.save(project);
		String inHouse = project.getInHouse();
		if (!inHouse.equals("") && !inHouse.equals("null")) {
			if (!inHouse.equalsIgnoreCase("Yes") && !inHouse.equalsIgnoreCase("NO"))
				project.setInHouse("No");
		} else
			project.setInHouse("No");

		project.setCreatedAt(new Date());
		project.setCreatedBy(userDetail.getUserId());
		project.setLastUpdatedAt(new Date());
		project.setUpdatedBy(userDetail.getUserId());
		Projects projetAdded = projectRepository.save(project);

		if (projetAdded != null) {
			List<Feature> features = featureRepository.getDefaultFeatures();

			features.forEach(feature -> {
				ProjectFeature projectFeature = new ProjectFeature();
				projectFeature.setFeatureId(feature.getFeatureId());
				projectFeature.setProjectId(projetAdded.getpId());
				projectFeatureService.addProjectFeature(projectFeature);
			});
		}

		response.setSuccess(true);
		response.setMessage(ResponseMessages.PROJECT_ADDED);
		Map<String, String> content = new HashMap<String, String>();
		content.put("projectId", projetAdded.getpId());
		response.setContent(content);

		// }
		return response;
	}

	private ApiResponse validateProject(Projects projects) {
		ApiResponse response = new ApiResponse(true);

		if (projects.getProjectName() == null || projects.getProjectName().equals("")) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.Pro_Name_Man);
		} else if (projects.getProjectName().length() < 3) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.Pro_name_len);
		}

		if (projects.getProjectDescritpion() == null || projects.getProjectDescritpion().equals("")) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.Pro_desc_man);
		} else if (projects.getProjectDescritpion().length() < 15 || projects.getProjectDescritpion().length() > 5000) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.Pro_desc_len);
		}

		if (projects.getClientId() != null && projects.getClientId().length() > 0) {
			Client obj = clientRepository.getClientById(projects.getClientId());
			if (obj == null) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ClIENT_ID_VALID);
			}
		}
		return response;
	}

	@Override
	public ApiResponse getAllProjects(Pageable pageable) {
		/*
		 * // Page<Map> projectList = projectRepository.getAllProjectLsit(pageable);
		 */
		Page<Map> projectList;

		System.out.println(userDetail.getUserRole());

		if (userDetail.getUserRole() != null && (userDetail.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN")
				|| userDetail.getUserRole().equalsIgnoreCase("INFRA_ADMIN"))) {
			projectList = projectRepository.getAllForAdmins(pageable);

		} else
			projectList = projectRepository.getAllProjectsList(userDetail.getScopeId(), pageable);
		if (projectList != null) {

			Map content = new HashMap<>();
			content.put("projectList", projectList);
			ApiResponse response = new ApiResponse(true);
			response.setSuccess(true);
			response.setContent(content);
			return response;
		} else {
			ApiResponse response = new ApiResponse(true);

			response.setMessage("project list is empty");

			return response;
		}
	}

	@Override
	public ApiResponse getAllProjectsForTickets(String serachString) {
		/*
		 * // Page<Map> projectList = projectRepository.getAllProjectLsit(pageable);
		 */
		List<Map> projectList;

		System.out.println(userDetail.getUserRole());

		projectList = projectRepository.getAllProjectsForTickets(serachString);
		if (projectList != null) {

			Map content = new HashMap<>();
			content.put("projectList", projectList);
			ApiResponse response = new ApiResponse(true);
			response.setSuccess(true);
			response.setContent(content);
			return response;
		} else {
			ApiResponse response = new ApiResponse(true);

			response.setMessage("project list is empty");

			return response;
		}
	}

	@Override
	public ApiResponse getProjectDetailsById(String projectId) {

		ApiResponse response = new ApiResponse(false);
//		return projectRepository.findById(projectId).map(project -> {
//			return project;
//		}).orElseThrow(() -> new ResourceNotFoundException("project not found for id: " + projectId));

		Projects project = projectRepository.getProjecById(projectId);
		if (project == null) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.PROJECT_ID_VALID);
		} else {
			Map content = new HashMap<>();
			content.put("Project", project);
			response.setContent(content);
			response.setSuccess(true);
			response.setMessage("Retrieved successfully");
		}
		return response;
	}

	@Override
	public ApiResponse editProject(Projects projectRequest) {
		ApiResponse response = new ApiResponse(false);

		if (projectRequest.getpId() == null || projectRequest.getpId().equals("")) {
			response.setSuccess(false);
			response.setMessage("Project ID is Mandatory");
		} else {
			Projects projectid = projectRepository.getById(projectRequest.getpId());
			if (projectid == null) {

				response.setMessage(ResponseMessages.PROJECT_ID_VALID);
				response.setSuccess(false);
				response.setContent(null);

			}

			response = validateProject(projectRequest);
			if (response.getMessage() != null && response.getMessage() != "") {
				return response;
			}
			// ApiResponse response = validateClientIdProjectId(projectRequest);
			// if (response.isSuccess()) {

//			if (projectRequest != null) {
			projectRequest.setpId(projectRequest.getpId());
			projectRequest.setUpdatedBy(projectRequest.getUpdatedBy());
			projectRequest.setLastUpdatedAt(new Date());
			projectRequest.setInHouse(projectRequest.getInHouse());
			projectRequest.setProjectDescritpion(projectRequest.getProjectDescritpion());
			projectRequest.setProjectName(projectRequest.getProjectName());
			projectRequest.setStatus(projectRequest.getStatus());
			// projectRepository.save(projectRequest);
			Projects projetAdded = projectRepository.save(projectRequest);

			response.setSuccess(true);
			response.setMessage(ResponseMessages.PROJECT_EDIT);
			Map content = new HashMap();
			content.put("projectId", projetAdded.getpId());
			response.setContent(content);
		}
//			else {
//				response.setSuccess(false);
//				response.setMessage(ResponseMessages.PROJECT_ID_VALID);
//				response.setContent(null);
//			}
		// }
		return response;
	}

	public ApiResponse validateClientIdProjectId(Projects projects) {
		ApiResponse response = new ApiResponse(false);
		if (projects.getClientId() == null) {
			/*
			 * response.setMessage("success"); response.setSuccess(true);
			 * response.setContent(null);
			 */

			response.setMessage(ResponseMessages.ClIENT_ID_VALID);
			response.setSuccess(false);
			response.setContent(null);
		}

		System.out.println("projects.getClientId() " + projects.getClientId());
		System.out.println("projects.getpId() " + projects.getpId());
		Projects projectid = projectRepository.getById(projects.getpId());
		if (projectid == null) {
			/*
			 * response.setMessage("success"); response.setSuccess(true);
			 * response.setContent(null); } else {
			 */
			response.setMessage(ResponseMessages.PROJECT_ID_VALID);
			response.setSuccess(false);
			response.setContent(null);
		} else {
			response.setMessage(ResponseMessages.PROJECT_EDIT);
			response.setSuccess(true);
		}
		return response;
	}

	public ApiResponse validateProjectId(Projects projects) {
		ApiResponse response = new ApiResponse(false);
		if (projects.getpId() == null) {
			response.setMessage("success");
			response.setSuccess(true);
			response.setContent(null);
		} else {
			response.setMessage(ResponseMessages.PROJECT_ID_VALID);
			response.setSuccess(false);
			response.setContent(null);
		}
		return response;
	}

	@Override
	public ApiResponse getAllProjectsByDeveloper(Pageable pageable) {
		Page<Map> projectList;
		if (!userDetail.getUserRole().equalsIgnoreCase("DEVELOPER"))
			projectList = projectRepository.getAllProjectByEmployee(pageable);
		else {
			Employee employeeObj = employeeRepository.getbyUserByUserId(userDetail.getUserId());
			projectList = projectRepository.getAllProjectByDeveloper(pageable, employeeObj.geteId());
		}
		// @Query("Select distinct new map(e.projectId as projectId,p.projectName as
		// projectName) from ProjectMembers "
		// + "e left join Projects p On e.projectId = p.pId where e.status = 'ACTIVE'
		// and e.employeeId = :employeeId ")

		Map content = new HashMap();
		content.put("projectList", projectList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

	@Override
	public IssueTrackerResponse findById(String id) {
		IssueTrackerResponse response = new IssueTrackerResponse();

		List<Map> projectDetails = projectRepository.getByProjectClietName(id);
		response.setContent(projectDetails);

		response.setStatus("success");

		return response;

	}

	@Override
	public Projects getprojectById(String projectId) {
		return projectRepository.getProjecById(projectId);

	}

	@Override
	public ApiResponse searchProject(String searchString) {
		ApiResponse response = new ApiResponse(false);
		List<Map> projectList = projectRepository.searchProject(searchString, userDetail.getUserRole(),
				userDetail.getScopeId());
		Map content = new HashMap();
		if (projectList.size() > 0) {
			content.put("projectList", projectList);
			response.setSuccess(true);
			response.setContent(content);
		} else {
			content.put("projectList", projectList);
			response.setSuccess(false);
			response.setContent(content);
		}

		return response;
	}

	@Override
	public ApiResponse getgenericIssues() {

		List<Map> projectList = projectRepository.getgenericIssues();

//		Map content = new HashMap();
		Map<String, List<Map>> content = new HashMap<String, List<Map>>();
		content.put("projectList", projectList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

	@Override
	public ApiResponse getAllProjectList() {
		/*
		 * // Page<Map> projectList = projectRepository.getAllProjectLsit(pageable);
		 */
		List<Map> projectList;

		System.out.println(userDetail.getUserRole());
		if (userDetail.getUserRole() != null && (userDetail.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN")
				|| userDetail.getUserRole().equalsIgnoreCase("INFRA_ADMIN"))) {
			projectList = projectRepository.getAllProject();

		} else
			projectList = projectRepository.getAllProjectByDeveloperList(userDetail.getScopeId());

		Map content = new HashMap();
		content.put("projectList", projectList);
		ApiResponse response = new ApiResponse(true);
		response.setSuccess(true);
		response.setContent(content);
		return response;
	}

	private ApiResponse validateStatus(ProjectStatus projectStatus) {
		ApiResponse response = new ApiResponse(false);
		if (projectStatus == ProjectStatus.ACTIVE || projectStatus == ProjectStatus.INACTIVE
				|| projectStatus == ProjectStatus.INDEVELOPMENT || projectStatus == ProjectStatus.INPRODUCTION) {
			response.setMessage(ResponseMessages.STATUS_UPDATE);
			response.setSuccess(true);
		}

		else {
			response.setMessage("project status is invalid");
			response.setSuccess(false);

		}

		return response;
	}

	@Override
	public ApiResponse updateProjectStatus(String projectId, ProjectStatus projectStatus) {

		ApiResponse response = validateStatus(projectStatus);
		if (response.isSuccess()) {
			Projects projects = projectRepository.getById(projectId);
			if (projects != null) {

				projects.setStatus(projectStatus);
				projectRepository.save(projects);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.STATUS_UPDATE);
				response.setContent(null);
			}
		} else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.PROJECT_ID_VALID);
			response.setContent(null);
		}

		return response;
	}

}