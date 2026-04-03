package com.personal_finance_manager_api.notifications;

import com.personal_finance_manager_api.budget.OverBudgetCalculator;
import com.personal_finance_manager_api.exceptions.ModelNotFoundException;
import com.personal_finance_manager_api.models.User;
import com.personal_finance_manager_api.repositories.UserRepository;
import com.personal_finance_manager_api.services.EmailService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OverBudgetNotification {

    private final EmailService emailService;
    private final OverBudgetCalculator overBudgetCalculator;

    public OverBudgetNotification(EmailService emailService, OverBudgetCalculator overBudgetCalculator) {
        this.emailService = emailService;
        this.overBudgetCalculator = overBudgetCalculator;
    }

    public void sendOverBudgetNotification(Integer month, User user) throws ModelNotFoundException {
        Map<String, Object> overBudgetInfo = overBudgetCalculator.isOverBudget(month);
        boolean isOverBudget = (boolean) overBudgetInfo.get("overBudget");

        if(isOverBudget) {
            this.emailService.sendSimpleEmail(
                    user.getEmail(),
                    "Over Budget Alert",
                    "You have exceeded your budget for this month by "+overBudgetInfo.get("amount")+". Please review your transactions and adjust your spending accordingly."
            );
        }
    }
}
