INSERT INTO role (id, name)
VALUES ('c733fdec-4c9f-4ae4-8266-c073dc7d3127', 'ROLE_ADMIN'),
       ('6e27aa40-ae4e-42c0-81a4-869560b59968', 'ROLE_USER');

INSERT INTO permission (id, name)
VALUES ('d06873b0-e76f-40f7-8e55-8811958e7ec2', 'CREATE_NOTE'),
       ('db8be56d-542c-4d0a-a184-c4ba5aaac05c', 'UPDATE_NOTE'),
       ('792d64c9-80a6-4cea-83f7-7cb8cd14f75b', 'DELETE_NOTE'),
       ('54698e65-e150-4803-9813-47a9eb8b1627', 'DELETE_USER'),
       ('dca72f16-6706-48de-937d-4a4f6abf7faf', 'LOCK_USER');

INSERT INTO role_permission (role_id, permission_id)
VALUES ('c733fdec-4c9f-4ae4-8266-c073dc7d3127', 'd06873b0-e76f-40f7-8e55-8811958e7ec2'),
       ('6e27aa40-ae4e-42c0-81a4-869560b59968', 'd06873b0-e76f-40f7-8e55-8811958e7ec2'),
       ('c733fdec-4c9f-4ae4-8266-c073dc7d3127', 'db8be56d-542c-4d0a-a184-c4ba5aaac05c'),
       ('c733fdec-4c9f-4ae4-8266-c073dc7d3127', '792d64c9-80a6-4cea-83f7-7cb8cd14f75b'),
       ('6e27aa40-ae4e-42c0-81a4-869560b59968', 'db8be56d-542c-4d0a-a184-c4ba5aaac05c'),
       ('6e27aa40-ae4e-42c0-81a4-869560b59968', '792d64c9-80a6-4cea-83f7-7cb8cd14f75b'),
       ('c733fdec-4c9f-4ae4-8266-c073dc7d3127', '54698e65-e150-4803-9813-47a9eb8b1627'),
       ('c733fdec-4c9f-4ae4-8266-c073dc7d3127', 'dca72f16-6706-48de-937d-4a4f6abf7faf');

INSERT INTO public.user (id, user_name, first_name, last_name, email, role_id, "password", is_account_non_locked)
VALUES ('fc2487bb-3b28-4645-bc5c-7ee63e561160', 'Shade', 'Shade', 'Slay', 'shade@shade.ga',
        'c733fdec-4c9f-4ae4-8266-c073dc7d3127', '$2a$10$9bCC790MnxRPoyi1os5TbuMEdi1o49G38oKXkMMNLVftyJoJ0/zYe', true),
       ('ab6a14d0-7467-49c8-b043-afce20c924c8', 'Yukki', 'Yukki', 'Slay', 'yukki@shade.ga',
        'c733fdec-4c9f-4ae4-8266-c073dc7d3127', '$2a$10$t4kNX8rQO24KOEoqvN7ZGemTxUTwZ0Dy3mSl5f0bLZqRlWIN7tyh.', true);

