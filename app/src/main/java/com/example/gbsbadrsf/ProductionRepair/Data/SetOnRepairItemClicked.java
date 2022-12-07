package com.example.gbsbadrsf.ProductionRepair.Data;

import com.example.gbsbadrsf.Model.ManufacturingDefect;

public interface SetOnRepairItemClicked {
    void onRepairItemClicked(ManufacturingDefect defectsManufacturing, int position, int pending);
}
