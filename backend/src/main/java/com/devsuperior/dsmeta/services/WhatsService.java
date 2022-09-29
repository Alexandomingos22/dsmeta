package com.devsuperior.dsmeta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

import com.twilio.Twilio; 
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber; 
 
 
@Service
public class WhatsService { 
	
	@Value("${twilio.sid}")
	private String twilioSid;

	@Value("${twilio.key}")
	private String twilioKey;
	
	@Value("${twilio.whats.from}")
	private String twilioWhatsFrom;

	@Value("${twilio.whats.to}")
	private String twilioWhatsTo;
	
	@Value("${twilio.whats.body}")
	private String twilioWhatsBody;
	
	@Autowired
	private SaleRepository saleRepository;
 
    public void sendWhats(Long saleId)  { 
    	
        Sale sale = saleRepository.findById(saleId).get();
		
		String date = sale.getDate().getMonthValue() + "/" + sale.getDate().getYear();
		
		String msg = "Parabéns " + sale.getSellerName() + " você foi destaque em " + date
				+ " com um salário de R$" + String.format("%.2f", sale.getAmount());

    	
        Twilio.init(twilioSid, twilioKey); 
        
        PhoneNumber to = new PhoneNumber(twilioWhatsFrom);
		PhoneNumber from = new PhoneNumber(twilioWhatsTo);
	
        
        Message message = Message.creator(to, from, msg).create(); 
 
        System.out.println(message.getSid()); 
    } 
}


