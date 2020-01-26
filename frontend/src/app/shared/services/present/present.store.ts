import { Injectable } from '@angular/core';
import { EntityState, StoreConfig, EntityStore } from '@datorama/akita';

import { Present } from 'app/shared/model/present.model';

export interface PresentState extends EntityState<Present> { }

@Injectable({ providedIn: 'root' })
@StoreConfig({ name: 'presents' })
export class PresentStore extends EntityStore<PresentState, Present> {
  constructor() {
    super({});
  }
}
