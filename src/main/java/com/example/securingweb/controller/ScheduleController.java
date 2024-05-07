/**
 * 
 */
package com.example.securingweb.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.securingweb.AppProperties;
import com.example.securingweb.entities.Brand;
import com.example.securingweb.entities.BusinessRule;
import com.example.securingweb.entities.PMS;
import com.example.securingweb.entities.Region;
import com.example.securingweb.entities.Response;
import com.example.securingweb.entities.Role;
import com.example.securingweb.entities.Schedule;
import com.example.securingweb.entities.SearchDrpDwns;
import com.example.securingweb.entities.SearchEvents;
import com.example.securingweb.entities.SendEvents;
import com.example.securingweb.entities.ServiceResponse;
import com.example.securingweb.entities.Ship;
import com.example.securingweb.entities.TransmissionType;
import com.example.securingweb.entities.User;
import com.example.securingweb.repository.BrandRepository;
import com.example.securingweb.repository.BusinessRuleRepository;
import com.example.securingweb.repository.PMSRepository;
import com.example.securingweb.repository.RegionRepository;
import com.example.securingweb.repository.ScheduleRepository;
import com.example.securingweb.repository.ShipRepository;
import com.example.securingweb.repository.TransmissionTypeRepository;
import com.example.securingweb.repository.UserRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
//@Secured(SecurityUtil.MANAGE_USERS)
public class ScheduleController //extends BaseController
{	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired private CalendarService calendarService;
//	@Autowired protected SecurityService securityService;
//	@Autowired protected EmployeeStatusRepository employeeStatusRepository;
//	@Autowired protected RoleRepository roleRepository;
	@Autowired protected UserRepository userRepository;
//	@Autowired protected CalendarValidator calendarValidator;
//	@Autowired protected AllStatusValidator allStatusValidator;
	
		
	@Autowired private ScheduleRepository scheduleRepository;
	//@Autowired private ScheduleValidator scheduleValidator;
	@Autowired private BrandRepository brandRepository;
	@Autowired private ShipRepository shipRepository;
	@Autowired private TransmissionTypeRepository transmissionTypeRepository;
	@Autowired private PMSRepository pmsRepository;
	@Autowired private RegionRepository regionRepository;
	//@Autowired private EmailHelper emailHelper;
	@Autowired private BusinessRuleRepository businessRuleRepository;
	//@Autowired private NotificationService notificationService;

   private AppProperties app;
    
    
    
	private static final String viewPrefix = "schedule/";
	
    
	/*
	 * @Override protected String getHeaderTitle() { return
	 * "Manage Employees Schedules"; }
	 */    
    @Autowired
    public void setApp(AppProperties app) {
        this.app = app;
    }
	
	
//	@Override
//	protected String getHeaderTitle() {
//		return "Schedule";
//	}	
	
	@RequestMapping({"/home","/calendar"})
	public String home(Model model, @RequestParam(value="year", required=false) String strYear)
	{
		List<Brand> brands = brandRepository.findAll();
		model.addAttribute("brands",brands);
		
		List<Ship> ships = shipRepository.findAllByOrderByCodeAsc();
		model.addAttribute("ships", ships);
		
		List<TransmissionType> trans = transmissionTypeRepository.findAll();
		model.addAttribute("trans", trans);
		
		List<Region> regions = regionRepository.findAll();
		model.addAttribute("regions", regions);
		
		List<PMS> pmss = pmsRepository.findAll();
		model.addAttribute("pmss", pmss);
		
		model.addAttribute("app", this.app);
		System.out.println("***** app:"+ this.app.getIpAddress());
		
		return "schedule/calendar";
	}
	
	@RequestMapping(value="/schedule/events", method=RequestMethod.GET)
	@ResponseBody
	public List<Schedule> events(Model model)
	{		
		List<Schedule> events= scheduleRepository.findAll();
		SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );   
		Calendar cal = Calendar.getInstance();
		
		for (Schedule schd : events) {
				cal.setTime(schd.getStartDate());
				cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(schd.getStartTime()));
				String convertedDate=dateFormat.format(cal.getTime()); 
				//System.out.println(convertedDate);
				try {
					Date d = dateFormat.parse(convertedDate);
					schd.setStartDate(d);
					//System.out.println(d);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		return events;
	}
	
	//@Secured({SecurityUtil.MANAGE_USERS,SecurityUtil.MANAGE_CALENDAR})
	@RequestMapping(value="/schedule/SaveEvent", method=RequestMethod.POST,  produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response saveEvents(@RequestBody Schedule schedule, HttpServletRequest request, HttpServletResponse response)
	{	
		SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );   
		Calendar cal = Calendar.getInstance();    
		cal.setTime(schedule.getStartDate());    
		cal.add( Calendar.DATE, 1 );    
		String convertedDate=dateFormat.format(cal.getTime());
		System.out.println("----------------------------------------------"+convertedDate);
		try {
			Date d = dateFormat.parse(convertedDate);
			schedule.setStartDate(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getLocalizedMessage());
			//e.printStackTrace();
		}
		//adding the addition parameters into schedule instance
		Ship ship = shipRepository.getById(Integer.parseInt(schedule.getShip_id()));
		schedule.setBrand_id(ship.getBrand().getId().toString());
		schedule.setPms_id(ship.getPms().getId().toString());
		/////schedule.setTransmission_type_id(ship.getTrasmissionTypeId);  ///kp adding this to capture the transmission type 
		
		Region region = new Region();
		region.setId(Integer.parseInt(schedule.getRegion_id()));
		
		TransmissionType transmissionType = new TransmissionType();
		transmissionType.setId(Integer.parseInt(schedule.getTransmission_type_id()));
		
		BusinessRule businessRule = businessRuleRepository
				.findBusinessRuleByPmsRegionTransmission(ship.getPms(), region, transmissionType);
		
		schedule.setBusinessRule(businessRule);
		if(schedule.getId() == 0) {
//			schedule.setUser(getCurrentUser().getUser());
			schedule.setVoyageNumber("-88888");
			scheduleRepository.save(schedule);
		} else {
			Schedule scheduleUp = scheduleRepository.getById(schedule.getId());
			scheduleUp.setTitle(schedule.getTitle());
			scheduleUp.setDescription(schedule.getDescription());
			scheduleUp.setStartTime(schedule.getStartTime());
			scheduleUp.setStartDate(schedule.getStartDate());
			scheduleUp.setStatus(schedule.getStatus());
			scheduleUp.setIsManualEntry(schedule.getIsManualEntry());
			
			
			
			
			//***new
			
			scheduleUp.setTransmission_type_id(schedule.getTransmission_type_id());
			scheduleUp.setRegion_id(schedule.getRegion_id());
			scheduleUp.setShip_id(schedule.getShip_id());

			
			
			transmissionType.setId(Integer.parseInt(schedule.getTransmission_type_id()));
			
			businessRule = businessRuleRepository
					.findBusinessRuleByPmsRegionTransmission(ship.getPms(), region, transmissionType);
			
			scheduleUp.setBusinessRule(businessRule);

			
			////scheduleUp.setRegion_id(String.valueOf(region.getId())); //// just added this
			
			//scheduleUp.setTransmission_type_id(transmission_type_id); //// just added this
			
			scheduleRepository.save(scheduleUp);
		}
		 
		 Response res = new Response("Done", schedule);
		 //notificationService.notify(schedule);
		 
		 return res;
	}
	
	//@Secured(SecurityUtil.MANAGE_EVENT_STATUS)
	@RequestMapping(value="/schedule/changeStatus", method=RequestMethod.POST,  produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response changeStatus(@RequestBody Schedule schd, HttpServletRequest request, HttpServletResponse response)
	{	
		if (schd.getId() != null ) {
			scheduleRepository.updateEventStatus(schd.getId(), schd.getStatus());
		}

//		Schedule schedule = scheduleRepository.getOne(schd.getId());
//		schedule.setStatus(schd.getStatus());
//		scheduleRepository.save(schedule);
		 Response res = new Response("Done", null);
		 //notificationService.notify(schd);
		 return res;
	}
	
	//@Secured({SecurityUtil.MANAGE_USERS,SecurityUtil.MANAGE_CALENDAR})
	@RequestMapping(value="/schedule/del", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response deleteEvent(@RequestBody Schedule schd, HttpServletRequest request, HttpServletResponse response){
		Schedule schedule = scheduleRepository.getById(schd.getId());
		Response res = new Response("", null);

		try {

			scheduleRepository.manualDelete(schd.getId(), 1);
			res = new Response("Done", null);
		} catch (Exception e) {
			res = new Response("Error", null);
			//redirectAttributes.addFlashAttribute("error", "Schedule " + schedule.getTitle()+ " cannot be Deleted. It belongs to the Calender!!");
		}	
		//notificationService.notify(schd);
		return res;
	}
	
	//Search the events as per the selected criteria
	@RequestMapping(value="/schedule/searchEvents", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Schedule> searchEvents(@RequestBody SearchEvents sEvent, HttpServletRequest request, HttpServletResponse response){
		
		
		
		List<Schedule> schedule = scheduleRepository.findAll(new Specification<Schedule>() {

			
			
			@Override
			public Predicate toPredicate(Root<Schedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				
				
				//if date range is present in search
				if (sEvent.getDateRange() != null && !sEvent.getDateRange().isEmpty()) {
					String[] dates = sEvent.getDateRange().split("-");
					SimpleDateFormat dateFormat1 = new SimpleDateFormat( "MM/dd/yyyy" );
					SimpleDateFormat dateFormat2 = new SimpleDateFormat( "yyyy-MM-dd" );
					Date d1 = null;
					Date d2 = null;
					try {
						d1 = dateFormat2.parse(dateFormat2.format(dateFormat1.parse(dates[0].trim())));
						d2 = dateFormat2.parse(dateFormat2.format(dateFormat1.parse(dates[1].trim())));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					predicates.add(cb.between(root.get("startDate"), d1, d2));
				}
				//if brand is present in search
				if (sEvent.getBrandId() != null && sEvent.getBrandId() != 0) {
					//brandId should be the DB field
					predicates.add(cb.equal(root.get("brand_id"), sEvent.getBrandId()));
				}
//				//if shipId is present in search
				if (sEvent.getShipId() != null && sEvent.getShipId() != 0) {
					//shipId should be the DB field
					predicates.add(cb.equal(root.get("ship_id"), sEvent.getShipId()));
				}
//				//if transmission_type_Id is present in search
				if (sEvent.getTransmissionTypeId() != null && sEvent.getTransmissionTypeId() != 0) {
					//shipId should be the DB field
					predicates.add(cb.equal(root.get("transmission_type_id"), sEvent.getTransmissionTypeId()));
				}
//				//if pms_Id is present in search
				if (sEvent.getPmsId() != null && sEvent.getPmsId() != 0) {
					//shipId should be the DB field
					predicates.add(cb.equal(root.get("pms_id"), sEvent.getPmsId()));
				}
//				//if region_Id is present in search
				if (sEvent.getRegionId() != null && sEvent.getRegionId() != 0) {
					//shipId should be the DB field
					predicates.add(cb.equal(root.get("region_id"), sEvent.getRegionId()));
				}
				predicates.add(cb.equal(root.get("isDeleted"), 0));
				return cb.and(predicates.toArray(new Predicate[0]));
			}
			
		});
		
		SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );   
		Calendar cal = Calendar.getInstance();
		
		List<TransmissionType> transColor = transmissionTypeRepository.findAll();
		
		for (Schedule schd : schedule) {
			//schd.setColor(schd.tr);
			 for(TransmissionType t: transColor){
				 if(t.getId().toString().equals(schd.getTransmission_type_id())){
					 String cssclass = t.getStyle();
					 if(schd.getIsManualEntry() == 1){
						 cssclass += " edited";
					 }
					 schd.setColor(cssclass);
				 }
			 }
			
//				cal.setTime(schd.getStartDate());
//				cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(schd.getStartTime()));
//				String convertedDate=dateFormat.format(cal.getTime()); 
//				//System.out.println(convertedDate);
//				try {
//					Date d = dateFormat.parse(convertedDate);
//					schd.setStartDate(d);
//					//System.out.println(d);
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println("*********** "+schd.getId() + " ++++ "+schd.getStatus());
		}
		

			return schedule;
	}
	
	/***
	
	//Send email events
	@RequestMapping(value="/schedule/sendEmail", method=RequestMethod.POST,  produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response sendEmail(@RequestBody SendEvents event, HttpServletRequest request, HttpServletResponse response) throws MessagingException
	{	
		boolean status = false;
		int count1 = 0;
		int count2 = 0;
		String htmlMessageBody = null;
		if(event.getChecked().equals("1")) {
			Date td = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );   
			Calendar cal = Calendar.getInstance();    
			cal.setTime(td);    
			cal.add( Calendar.DATE, 1 );    
			String convertedDate=dateFormat.format(cal.getTime());    
			//System.out.println("Date increase by one.. "+convertedDate);
			List<Schedule> scdTom = null;
			try {
				Date tomdate = dateFormat.parse(convertedDate);
				scdTom = scheduleRepository.findTomEvents(tomdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Schedule> scdTod = scheduleRepository.findTdyEvents();
			count1 = scdTod.size();
			count2 = scdTod.size();
			System.out.println(scdTod.size()  +" +++++++++++++++ "+scdTom.size());
			//htmlMessageBody = emailHelper.formatMessageContentForTdyTom(scdTod,scdTom);
			if(count1 > 0 || count2 > 0) {
				//status = emailHelper.sendEmail(event.getEmail(), Constant.eventEmailSubject, htmlMessageBody);
				System.out.println("Sent today tom email");
			}
			
		} else {
			//get html message content
			
			SearchEvents SearchEventFiltered = new SearchEvents(event.getBrandId(), event.getShipId(), event.getTransmissionTypeId(), event.getRegionId(), event.getPmsId(), event.getDateRange());
			List<Schedule> filteredSchedule  = searchEvents(SearchEventFiltered, request, response);
			
			 //htmlMessageBody = emailHelper.formatMessageContent(filteredSchedule);
			 //status = emailHelper.sendEmail(event.getEmail(), Constant.eventEmailSubject, htmlMessageBody);
			 System.out.println("Sent all events");
		}
		 //send email

		 Response res = null;
		 if (status) {
			 res = new Response("Done", null);
		 } else {
			 res = new Response("Error", null);
		 }
		 if (count1 == 0 && count2 == 0 && event.getChecked().equals("1")) {
			 res = new Response("Nodata", null);
		 }
		 
		 return res;
	}

	***/
/////////////////////////////METHODS FOR WEBSERVICES//////////////////////////////		
		@RequestMapping(value="/schedules", method=RequestMethod.GET)
		public String listSchedule(Model model){
			List<Schedule> schedules = scheduleRepository.findAll();
			model.addAttribute("schedules", schedules);
			return viewPrefix+"schedules";
			
		}
		
		
		/****
		@RequestMapping(value="/schedules/new", method=RequestMethod.GET)
		public String createNewSchedule(Model model){
			
			Schedule schedule = new Schedule();
			model.addAttribute("schedule", schedule);
			
			List<User> allUsers = null;
			User user = userRepository.getById(getCurrentUser().getUser().getId());
			List<Role> usersRoles = user.getRoles();
			logger.debug("Rehan is outside IF :{}", user.getName());
			
			boolean admin = false;
			for (Role role : usersRoles) {
				if(role.getId().equals(1)||role.getId().equals(2)){
					logger.debug("Siddiqui is inside IF list users :{}", role.getId());
					admin = true;
				}
			}
				if(admin == true){
					allUsers = this.securityService.getAllUsers();	
					logger.debug("ADD all users");
				}else{				
					allUsers = new ArrayList<>();
					allUsers.add(getCurrentUser().getUser());
					logger.debug("ADD current users :{}",getCurrentUser().getUser().getName());
				}
			

			
			model.addAttribute("user", allUsers);
			
			return viewPrefix+"create_schedule";
		}
		
		***/
		
		
		/***
		
		@RequestMapping(value="/schedules/new", method=RequestMethod.POST)
		public String newEventEntry( @Valid @ModelAttribute("schedule") Schedule schedule, 
				 BindingResult result, Model model, RedirectAttributes redirectAttributes){
			
			//scheduleValidator.validate(schedule, result);
			if(result.hasErrors()){
				logger.debug("Schedule validate function has the error ---- REHAN");
				return viewPrefix+"create_schedule";
			}
			schedule.setUser(getCurrentUser().getUser());
			scheduleRepository.save(schedule);
			redirectAttributes.addFlashAttribute("info", "Schedule created successfully");
			return "redirect:/schedules";
		}
		**/
		
		@RequestMapping(value="/schedules/{id}", method=RequestMethod.GET)
		public String editScheduleEntryGet(@PathVariable Integer id, Model model){
			Schedule schedule = scheduleRepository.getOne(id);
			model.addAttribute("schedule", schedule);
			
			return viewPrefix+"edit_schedule";
		}
		
		@RequestMapping(value="/schedules/{id}", method=RequestMethod.POST)
		public String editScheduleEntryPost(Model model, @ModelAttribute("schedule") Schedule schedule, RedirectAttributes redirectAttributes){
			
			scheduleRepository.save(schedule);
			redirectAttributes.addFlashAttribute("info", "Event modified successfully");
			return "redirect:/schedules";
		}
		
		@RequestMapping(value="/schedules/del/{id}", method=RequestMethod.GET)
		public String deleteEventEntry(@Valid @ModelAttribute("schedule") Schedule schedule, BindingResult result, 
				Model model, RedirectAttributes redirectAttributes, @PathVariable Integer id){
			schedule = scheduleRepository.getOne(id);
//			statusRepository.delete(empStatus);
//			statusValidator.validate(employeeStatus, result);
//			if(employeeScheduleRepository.findbyStatus(employeeStatus.getId()) != null){
			try {
		//			return "redirect:/events";
		//		}
//				schedule.setIsManualDelete(1);
//				schedule.setIsManualEntry(1);
//				scheduleRepository.save(schedule);
				scheduleRepository.delete(schedule);
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("error", "Schedule " + schedule.getTitle()+ " cannot be Deleted. It belongs to the Calender!!");
			}
				return "redirect:/schedules";
		}
		
		
		/*@RequestMapping(value="/schedule/transmissionDetails", method=RequestMethod.GET)
		public List<TransmissionType> editScheduleEntryPost(@RequestBody String shipId){
			System.out.println("shipId********** "+shipId);
			System.out.println("response of store proc ***********"+scheduleRepository.findTransmissionByShipType(Integer.parseInt(shipId)));
			return scheduleRepository.findTransmissionByShipType(Integer.parseInt(shipId));
			
		}*/
		
//		@Secured(SecurityUtil.MANAGE_USERS)
		@RequestMapping(value="/schedule/transmissionDetails", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public List<TransmissionType> getTransmissionDetails(@RequestBody Ship ship){
			Ship shipDerived = shipRepository.getById(ship.getId());
			Brand brand = shipDerived.getBrand();
			List<TransmissionType> transmissionTypes = transmissionTypeRepository.findByBrandId(brand.getId());
			return transmissionTypes;
		}
		
		/*@Secured(SecurityUtil.MANAGE_USERS)
		@RequestMapping(value="/schedule/getEditEvents", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public Schedule getEditEventDetails(@RequestBody Schedule schedule, HttpServletRequest request, HttpServletResponse response){
			Schedule schdl = scheduleRepository.findOne(schedule.getId());
			System.out.println("schedule details**********"+schdl.getStartTime()+" "+schdl.getShip_id());
			return scheduleRepository.findOne(schedule.getId());
		}*/
		
//		@Secured(SecurityUtil.MANAGE_USERS)
		@RequestMapping(value="/schedule/getEditEvents", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public Schedule getEditEventDetails(@RequestBody SearchEvents events, HttpServletRequest request, HttpServletResponse response) {
			System.out.println("scheduleId************************"+events.getId());
			Schedule schedule = scheduleRepository.getById(events.getId());
			return schedule;
		}
		
//		@Secured(SecurityUtil.MANAGE_USERS)
		@RequestMapping(value="/schedule/transAndShipDetails", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public SearchDrpDwns getSearchDropdowns(@RequestBody Brand brand)
		{
			System.out.println("transAndShipDetails called on ServerSide");
			SearchDrpDwns searchDrpDwns = new SearchDrpDwns();
			List<Ship> shipDropDwn = new ArrayList<>();
			if(brand.getId() > 0){
				shipDropDwn = shipRepository.findByBrandIdOrderByCodeAsc(brand.getId());
					
			}else{
				shipDropDwn = shipRepository.findAllByOrderByCodeAsc();
				
			}
			System.out.println(shipDropDwn);
			searchDrpDwns.setShips(shipDropDwn);
			List<TransmissionType> transmissionTypes = new ArrayList<>();
			if(brand.getId() > 0)
			{
				transmissionTypes = transmissionTypeRepository.findByBrandId(brand.getId());
			}else{
				transmissionTypes = transmissionTypeRepository.findAll();
			}
			
			System.out.println(transmissionTypes);
			searchDrpDwns.setTransmissionTypes(transmissionTypes);
			return searchDrpDwns;
		}
		
		@RequestMapping(value="/schedule/refreshCalendar", method=RequestMethod.GET,  produces=MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public Response refreshCalendar(HttpServletRequest request, HttpServletResponse response) throws Exception //MessagingException
		{	
			int returnCode = -1;
			boolean wait;
			boolean ended = false;
			int numTries = 0;
			
			ServiceResponse sr = new ServiceResponse();
			logger.info(new Date() + "Service Invoked!!!" + "  ipAddress: "+ app.getIpAddress() + " Service Location: " + app.getServiceLocation());
			String errorMessage = "";
			
			try 
			{
				
				Process ipProcess =  Runtime.getRuntime().exec(app.getServiceLocation());
			
				/*****
				BufferedReader input = new BufferedReader(new InputStreamReader(
						ipProcess.getInputStream()));

			         String line = null;

			         while ((line = input.readLine()) != null)
			         {
			            logger.info(line);
			         }
				****/
				
				ipProcess.waitFor();
				
				returnCode = ipProcess.exitValue();
				if (ipProcess.exitValue() != 0) 
				{
				    errorMessage = 
					        new String(toByteArray(ipProcess.getErrorStream()));
					logger.info("Error Invoking Calendar Refresh :" +app.getServiceLocation());
					logger.info("Process Error Msg :" +errorMessage);

				}

				sr.setReturnCode(returnCode);
				if (returnCode == 0) {
					sr.setStatus("Calendar Was refreshed!, please refresh the screen...");
				}
				else 
					sr.setStatus("Error invoking the Calendar refresh!");		
				
				logger.info("Calendar Refresh returnCode:" +returnCode);
			}
			catch (SecurityException  se)
			{
				logger.info("Security Error Creating the process"+se.getMessage());
			}
			catch (IllegalThreadStateException e)
			{
				sr.setStatus("Process has not exited.");		
				logger.info(e.getMessage());
			}
			catch (Exception e) 
			{ 
				sr.setStatus("Error invoking the Calendar refresh!");		
				logger.info(e.getMessage());
			}	
					
		

			Response res = new Response("Done", sr);
		
									
			logger.info(new Date() + "Existing the Service..." + "Service Call Status: " +sr.getReturnCode() +", "+ sr.getStatus());
					
			return res;
			
		}
		private static byte[] toByteArray(InputStream inputStream) throws IOException
		{
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    byte buffer[] = new byte[8000];
		    while (true)
		    {
		        int read = inputStream.read(buffer);
		        if (read == -1)
		        {
		            break;
		        }
		        baos.write(buffer, 0, read);
		    }
		    return baos.toByteArray();
		}


}