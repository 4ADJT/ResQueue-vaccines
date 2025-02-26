ALTER TABLE batches
    ADD COLUMN clinic_id UUID NOT NULL,
    ADD COLUMN eliminated TIMESTAMP NULL;

CREATE TABLE clinic (
                        id UUID PRIMARY KEY,
                        clinic_id UUID NOT NULL,
                        user_id UUID NOT NULL,
                        active BOOLEAN DEFAULT true
);

ALTER TABLE batches
    ADD CONSTRAINT fk_batches_clinic FOREIGN KEY (clinic_id) REFERENCES clinic(id);
