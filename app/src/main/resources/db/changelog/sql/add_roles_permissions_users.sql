INSERT INTO role (id, name)
VALUES ('c733fdec-4c9f-4ae4-8266-c073dc7d3127', 'ROLE_ADMIN'),
       ('6e27aa40-ae4e-42c0-81a4-869560b59968', 'ROLE_USER');

INSERT INTO permission (id, name)
VALUES ('f71edd14-9f5c-4ff8-82b8-2c68bb599b4f', 'USER_MINE_READ'),
       ('20f6a87d-4c6b-44b2-9d4a-2da67a667cee', 'USER_MINE_UPDATE'),
       ('0a79aace-bcc5-4feb-9599-ea8c3b7cbb1d', 'USER_OTHER_MANAGEMENT');

INSERT INTO role_permission (role_id, permission_id)
VALUES ('6e27aa40-ae4e-42c0-81a4-869560b59968', '20f6a87d-4c6b-44b2-9d4a-2da67a667cee'),
       ('6e27aa40-ae4e-42c0-81a4-869560b59968', 'f71edd14-9f5c-4ff8-82b8-2c68bb599b4f'),
       ('c733fdec-4c9f-4ae4-8266-c073dc7d3127', '0a79aace-bcc5-4feb-9599-ea8c3b7cbb1d');

INSERT INTO public.user (id, user_name, first_name, last_name, email, role_id, "password", is_account_non_locked)
VALUES ('fc2487bb-3b28-4645-bc5c-7ee63e561160', 'Shade', 'Shade', 'Slay', 'shade@shade.ga',
        'c733fdec-4c9f-4ae4-8266-c073dc7d3127', '$2a$10$9bCC790MnxRPoyi1os5TbuMEdi1o49G38oKXkMMNLVftyJoJ0/zYe', true),
       ('ab6a14d0-7467-49c8-b043-afce20c924c8', 'Yukki', 'Yukki', 'Slay', 'yukki@shade.ga',
        'c733fdec-4c9f-4ae4-8266-c073dc7d3127', '$2a$10$t4kNX8rQO24KOEoqvN7ZGemTxUTwZ0Dy3mSl5f0bLZqRlWIN7tyh.', true);