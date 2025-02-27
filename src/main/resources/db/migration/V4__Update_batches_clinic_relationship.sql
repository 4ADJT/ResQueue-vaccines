ALTER TABLE batches DROP CONSTRAINT IF EXISTS fk_batches_clinic;

ALTER TABLE batches ADD COLUMN IF NOT EXISTS clinic_id UUID NOT NULL;

ALTER TABLE clinic ADD CONSTRAINT unique_clinic_id UNIQUE (clinic_id);

ALTER TABLE batches
    ADD CONSTRAINT fk_batches_clinic
        FOREIGN KEY (clinic_id)
            REFERENCES clinic(clinic_id)
            ON DELETE CASCADE;
