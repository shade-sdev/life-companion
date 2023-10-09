INSERT INTO permission (id, name)
VALUES ('989004c5-2fe4-482e-a62d-ed69e21a667c', 'PERSON_MINE_READ'),
       ('dc54db4c-5d63-11ee-8c99-0242ac120002', 'PERSON_OTHER_READ'),
       ('8dd9c7d4-5d63-11ee-8c99-0242ac120002', 'PERSON_MINE_UPDATE'),
       ('35e6b8ae-5d61-11ee-8c99-0242ac120002', 'PERSON_OTHER_MANAGEMENT'),
       ('636196aa-5d61-11ee-8c99-0242ac120002', 'RELATIONSHIP_MINE_MANAGEMENT'),
       ('7bbb295a-5d61-11ee-8c99-0242ac120002', 'RELATIONSHIP_OTHER_MANAGEMENT'),
       ('827faaf4-5ec9-11ee-8c99-0242ac120002', 'RELATIONSHIP_OTHER_READ');

INSERT INTO role_permission (role_id, permission_id)
VALUES ('6e27aa40-ae4e-42c0-81a4-869560b59968', '989004c5-2fe4-482e-a62d-ed69e21a667c'),
       ('6e27aa40-ae4e-42c0-81a4-869560b59968', 'dc54db4c-5d63-11ee-8c99-0242ac120002'),
       ('6e27aa40-ae4e-42c0-81a4-869560b59968', '8dd9c7d4-5d63-11ee-8c99-0242ac120002'),
       ('c733fdec-4c9f-4ae4-8266-c073dc7d3127', '35e6b8ae-5d61-11ee-8c99-0242ac120002'),
       ('6e27aa40-ae4e-42c0-81a4-869560b59968', '636196aa-5d61-11ee-8c99-0242ac120002'),
       ('6e27aa40-ae4e-42c0-81a4-869560b59968', '827faaf4-5ec9-11ee-8c99-0242ac120002'),
       ('c733fdec-4c9f-4ae4-8266-c073dc7d3127', '7bbb295a-5d61-11ee-8c99-0242ac120002');

INSERT INTO public.person
(id, user_id, first_name, last_name, identity_visibility, house_number, street_name, locality, address_visibility,
 email_address, mobile_number, home_number, contact_visibility, initialized, created_by, created_date, last_modified_by,
 last_modified_date, "version")
VALUES ('abcbc41a-c11a-4a05-a2fc-8877296c65d3', 'fc2487bb-3b28-4645-bc5c-7ee63e561160', 'Shade',
        'Slay', 'STRANGER', 0, 'jaman', 'AGALEGA', 'STRANGER', 'shade@shade.ga', '51234567', '1234567',
        'STRANGER', true, 'system', '2023-10-02 22:18:13.450', 'Shade', '2023-10-02 22:19:25.986', 1),
       ('edf643c4-5a91-4882-bbfb-8a0d5d789625', 'ab6a14d0-7467-49c8-b043-afce20c924c8', 'Yukki',
        'Slay', 'STRANGER', 0, 'jaman', 'AGALEGA', 'STRANGER', 'yukki@shade.ga', '51234537', '1234567',
        'STRANGER', true, 'system', '2023-10-02 22:18:13.450', 'Shade', '2023-10-02 22:19:25.986', 1);