package com.klef.jfsd.donation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.klef.jfsd.donation.model.Donation;
import com.klef.jfsd.donation.model.Donor;
import com.klef.jfsd.donation.repository.DonationRepository;
import com.klef.jfsd.donation.service.DonationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DonationController {

    @Autowired
    private DonationService donationService;
    
    @Autowired
    private DonationRepository donationRepository;

    @GetMapping("donations")
    public String showDonationForm(Model model) {
        model.addAttribute("donation", new Donation());
        return "donationform";
    }

    @PostMapping("/donations")
    public String submitDonation(@ModelAttribute Donation donation) {
        donationService.saveDonation(donation);
        return "donationSuccess";  // You can show a success page or redirect
    }
    
    

    @RequestMapping("/mydonations")
    public String getDonations(HttpSession session, Model model) {
    	Donor d = (Donor) session.getAttribute("donor");
        if (d == null) {
            return "redirect:/login"; // Redirect to login if donorId doesn't exist
        }
        long donorId =(long) d.getId();
        List<Donation> donations = donationService.getDonationsByDonorId(donorId);
        model.addAttribute("donationList", donations);
        return "donationList"; // Return the view name
    }
    
    
    @GetMapping("/viewDonations")
    public String viewDonations(Model model) {
        List<Donation> donationList = donationRepository.findAll();
        model.addAttribute("donationList", donationList);
        return "viewdonations"; // JSP file
    }
    
    
    @GetMapping("/updatestatusofdonation")
    public String updateDonationStatus(@RequestParam("id") Long id, @RequestParam("status") String status) {
        donationRepository.updatedonationstatus(status, id);
        return "redirect:/viewDonations"; // Redirect back to the donation list page
    }
}
