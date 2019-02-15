
INSERT INTO `company` (`id`, `doc`, `update_date`, `creation_date`, `company_name`)
VALUES (NULL, '4535238532', CURRENT_DATE(), CURRENT_DATE(), 'HR');

INSERT INTO `employee` (`id`, `doc`, `update_date`, `creation_date`, `email`, `name`,
`profile`, `lunch_hours`, `work_hours_per_day`, `password`, `hour_rate`, `company_id`)
VALUES (NULL, '52372491', CURRENT_DATE(), CURRENT_DATE(), 'admin@hr.com', 'ADMIN', 'ROLE_ADMIN', NULL, NULL,
'$2a$06$xIvBeNRfS65L1N17I7JzgefzxEuLAL0Xk0wFAgIkoNqu9WD6rmp4m', NULL,
(SELECT `id` FROM `company` WHERE `doc` = '4535238532'));